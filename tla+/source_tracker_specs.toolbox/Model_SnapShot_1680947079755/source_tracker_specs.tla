------------------------ MODULE source_tracker_specs ------------------------
EXTENDS TLC, Integers, Sequences, FiniteSets

CONSTANTS NumWorker
ASSUME NumWorker > 0
NW == NumWorker

CONSTANTS NumFiles
ASSUME NumFiles > 0
NF == NumFiles

(*--algorithm source_tracker

variables
    fileSynchronizedQueue = <<>>;
    foundFiles = 0;
    availableFiles = 0;
    allFilesInQueue = 0;

macro wait(s) begin
  await s > 0;
  s := s - 1;
end macro;

macro signal(s) begin
  s := s + 1;
end macro;
    
fair process master \in 0..0
variables 
    file = "";
    i = 0;
begin Search:
    while foundFiles < NF do
        search:
            file := "file";
            fileSynchronizedQueue := Append(fileSynchronizedQueue, file);
            signal(availableFiles);
            foundFiles := foundFiles + 1;
    end while;
    signalAllFilesInQueue:
        while i < NW do
                signal(allFilesInQueue);
                i := i + 1;
        end while;
end process;

fair process worker \in 1..NW
variable file = "none";
begin Read:
    wait(allFilesInQueue);
    take:
        while fileSynchronizedQueue /= <<>> do
                wait(availableFiles);
                file := Head(fileSynchronizedQueue);
                fileSynchronizedQueue := Tail(fileSynchronizedQueue);
            count:
                print file;
        end while;
end process;


end algorithm;*)
\* BEGIN TRANSLATION (chksum(pcal) = "73c89f9b" /\ chksum(tla) = "26f05d")
\* Process variable file of process master at line 31 col 5 changed to file_
VARIABLES fileSynchronizedQueue, foundFiles, availableFiles, allFilesInQueue, 
          pc, file_, i, file

vars == << fileSynchronizedQueue, foundFiles, availableFiles, allFilesInQueue, 
           pc, file_, i, file >>

ProcSet == (0..0) \cup (1..NW)

Init == (* Global variables *)
        /\ fileSynchronizedQueue = <<>>
        /\ foundFiles = 0
        /\ availableFiles = 0
        /\ allFilesInQueue = 0
        (* Process master *)
        /\ file_ = [self \in 0..0 |-> ""]
        /\ i = [self \in 0..0 |-> 0]
        (* Process worker *)
        /\ file = [self \in 1..NW |-> "none"]
        /\ pc = [self \in ProcSet |-> CASE self \in 0..0 -> "Search"
                                        [] self \in 1..NW -> "Read"]

Search(self) == /\ pc[self] = "Search"
                /\ IF foundFiles < NF
                      THEN /\ pc' = [pc EXCEPT ![self] = "search"]
                      ELSE /\ pc' = [pc EXCEPT ![self] = "signalAllFilesInQueue"]
                /\ UNCHANGED << fileSynchronizedQueue, foundFiles, 
                                availableFiles, allFilesInQueue, file_, i, 
                                file >>

search(self) == /\ pc[self] = "search"
                /\ file_' = [file_ EXCEPT ![self] = "file"]
                /\ fileSynchronizedQueue' = Append(fileSynchronizedQueue, file_'[self])
                /\ availableFiles' = availableFiles + 1
                /\ foundFiles' = foundFiles + 1
                /\ pc' = [pc EXCEPT ![self] = "Search"]
                /\ UNCHANGED << allFilesInQueue, i, file >>

signalAllFilesInQueue(self) == /\ pc[self] = "signalAllFilesInQueue"
                               /\ IF i[self] < NW
                                     THEN /\ allFilesInQueue' = allFilesInQueue + 1
                                          /\ i' = [i EXCEPT ![self] = i[self] + 1]
                                          /\ pc' = [pc EXCEPT ![self] = "signalAllFilesInQueue"]
                                     ELSE /\ pc' = [pc EXCEPT ![self] = "Done"]
                                          /\ UNCHANGED << allFilesInQueue, i >>
                               /\ UNCHANGED << fileSynchronizedQueue, 
                                               foundFiles, availableFiles, 
                                               file_, file >>

master(self) == Search(self) \/ search(self) \/ signalAllFilesInQueue(self)

Read(self) == /\ pc[self] = "Read"
              /\ allFilesInQueue > 0
              /\ allFilesInQueue' = allFilesInQueue - 1
              /\ pc' = [pc EXCEPT ![self] = "take"]
              /\ UNCHANGED << fileSynchronizedQueue, foundFiles, 
                              availableFiles, file_, i, file >>

take(self) == /\ pc[self] = "take"
              /\ IF fileSynchronizedQueue /= <<>>
                    THEN /\ availableFiles > 0
                         /\ availableFiles' = availableFiles - 1
                         /\ file' = [file EXCEPT ![self] = Head(fileSynchronizedQueue)]
                         /\ fileSynchronizedQueue' = Tail(fileSynchronizedQueue)
                         /\ pc' = [pc EXCEPT ![self] = "count"]
                    ELSE /\ pc' = [pc EXCEPT ![self] = "Done"]
                         /\ UNCHANGED << fileSynchronizedQueue, availableFiles, 
                                         file >>
              /\ UNCHANGED << foundFiles, allFilesInQueue, file_, i >>

count(self) == /\ pc[self] = "count"
               /\ PrintT(file[self])
               /\ pc' = [pc EXCEPT ![self] = "take"]
               /\ UNCHANGED << fileSynchronizedQueue, foundFiles, 
                               availableFiles, allFilesInQueue, file_, i, file >>

worker(self) == Read(self) \/ take(self) \/ count(self)

(* Allow infinite stuttering to prevent deadlock on termination. *)
Terminating == /\ \A self \in ProcSet: pc[self] = "Done"
               /\ UNCHANGED vars

Next == (\E self \in 0..0: master(self))
           \/ (\E self \in 1..NW: worker(self))
           \/ Terminating

Spec == /\ Init /\ [][Next]_vars
        /\ \A self \in 0..0 : WF_vars(master(self))
        /\ \A self \in 1..NW : WF_vars(worker(self))

Termination == <>(\A self \in ProcSet: pc[self] = "Done")

\* END TRANSLATION 

=============================================================================
\* Modification History
\* Last modified Sat Apr 08 11:42:22 CEST 2023 by filip
\* Created Mon Apr 03 15:43:08 CEST 2023 by filip
