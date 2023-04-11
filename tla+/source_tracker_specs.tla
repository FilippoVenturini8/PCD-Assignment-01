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
    resultsSynchronizedQueue = <<>>;
    foundFiles = 0;
    availableFiles = 0;
    availableResult = 0;
    allFilesInQueue = 0;
    
define
    NumberOfResults == Len(resultsSynchronizedQueue) <= NF
    NumberOfFiles == Len(fileSynchronizedQueue) <= NF
end define;

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
    result = "";
    i = 0;
    j = 0;
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
    cumputeResults:
        while j < NF do
            waitResult:
                wait(availableResult);
            getResult:
                result := Head(resultsSynchronizedQueue);
                resultsSynchronizedQueue := Tail(resultsSynchronizedQueue);
            exposeResult:
                print result;
                j := j + 1;
        end while;
end process;

fair process worker \in 1..NW
variables 
    file = "none";
    result = "none";
begin Read:
    wait(allFilesInQueue);
    take:
        while fileSynchronizedQueue /= <<>> do
                wait(availableFiles);
                file := Head(fileSynchronizedQueue);
                fileSynchronizedQueue := Tail(fileSynchronizedQueue);
            count:
                result := "result";
            putResult:
                resultsSynchronizedQueue := Append(resultsSynchronizedQueue, result);
                signal(availableResult);
        end while;
end process;


end algorithm;*)
\* BEGIN TRANSLATION (chksum(pcal) = "54269c38" /\ chksum(tla) = "1b9f6456")
\* Process variable file of process master at line 38 col 5 changed to file_
\* Process variable result of process master at line 39 col 5 changed to result_
VARIABLES fileSynchronizedQueue, resultsSynchronizedQueue, foundFiles, 
          availableFiles, availableResult, allFilesInQueue, pc

(* define statement *)
NumberOfResults == Len(resultsSynchronizedQueue) <= NF
NumberOfFiles == Len(fileSynchronizedQueue) <= NF

VARIABLES file_, result_, i, j, file, result

vars == << fileSynchronizedQueue, resultsSynchronizedQueue, foundFiles, 
           availableFiles, availableResult, allFilesInQueue, pc, file_, 
           result_, i, j, file, result >>

ProcSet == (0..0) \cup (1..NW)

Init == (* Global variables *)
        /\ fileSynchronizedQueue = <<>>
        /\ resultsSynchronizedQueue = <<>>
        /\ foundFiles = 0
        /\ availableFiles = 0
        /\ availableResult = 0
        /\ allFilesInQueue = 0
        (* Process master *)
        /\ file_ = [self \in 0..0 |-> ""]
        /\ result_ = [self \in 0..0 |-> ""]
        /\ i = [self \in 0..0 |-> 0]
        /\ j = [self \in 0..0 |-> 0]
        (* Process worker *)
        /\ file = [self \in 1..NW |-> "none"]
        /\ result = [self \in 1..NW |-> "none"]
        /\ pc = [self \in ProcSet |-> CASE self \in 0..0 -> "Search"
                                        [] self \in 1..NW -> "Read"]

Search(self) == /\ pc[self] = "Search"
                /\ IF foundFiles < NF
                      THEN /\ pc' = [pc EXCEPT ![self] = "search"]
                      ELSE /\ pc' = [pc EXCEPT ![self] = "signalAllFilesInQueue"]
                /\ UNCHANGED << fileSynchronizedQueue, 
                                resultsSynchronizedQueue, foundFiles, 
                                availableFiles, availableResult, 
                                allFilesInQueue, file_, result_, i, j, file, 
                                result >>

search(self) == /\ pc[self] = "search"
                /\ file_' = [file_ EXCEPT ![self] = "file"]
                /\ fileSynchronizedQueue' = Append(fileSynchronizedQueue, file_'[self])
                /\ availableFiles' = availableFiles + 1
                /\ foundFiles' = foundFiles + 1
                /\ pc' = [pc EXCEPT ![self] = "Search"]
                /\ UNCHANGED << resultsSynchronizedQueue, availableResult, 
                                allFilesInQueue, result_, i, j, file, result >>

signalAllFilesInQueue(self) == /\ pc[self] = "signalAllFilesInQueue"
                               /\ IF i[self] < NW
                                     THEN /\ allFilesInQueue' = allFilesInQueue + 1
                                          /\ i' = [i EXCEPT ![self] = i[self] + 1]
                                          /\ pc' = [pc EXCEPT ![self] = "signalAllFilesInQueue"]
                                     ELSE /\ pc' = [pc EXCEPT ![self] = "cumputeResults"]
                                          /\ UNCHANGED << allFilesInQueue, i >>
                               /\ UNCHANGED << fileSynchronizedQueue, 
                                               resultsSynchronizedQueue, 
                                               foundFiles, availableFiles, 
                                               availableResult, file_, result_, 
                                               j, file, result >>

cumputeResults(self) == /\ pc[self] = "cumputeResults"
                        /\ IF j[self] < NF
                              THEN /\ pc' = [pc EXCEPT ![self] = "waitResult"]
                              ELSE /\ pc' = [pc EXCEPT ![self] = "Done"]
                        /\ UNCHANGED << fileSynchronizedQueue, 
                                        resultsSynchronizedQueue, foundFiles, 
                                        availableFiles, availableResult, 
                                        allFilesInQueue, file_, result_, i, j, 
                                        file, result >>

waitResult(self) == /\ pc[self] = "waitResult"
                    /\ availableResult > 0
                    /\ availableResult' = availableResult - 1
                    /\ pc' = [pc EXCEPT ![self] = "getResult"]
                    /\ UNCHANGED << fileSynchronizedQueue, 
                                    resultsSynchronizedQueue, foundFiles, 
                                    availableFiles, allFilesInQueue, file_, 
                                    result_, i, j, file, result >>

getResult(self) == /\ pc[self] = "getResult"
                   /\ result_' = [result_ EXCEPT ![self] = Head(resultsSynchronizedQueue)]
                   /\ resultsSynchronizedQueue' = Tail(resultsSynchronizedQueue)
                   /\ pc' = [pc EXCEPT ![self] = "exposeResult"]
                   /\ UNCHANGED << fileSynchronizedQueue, foundFiles, 
                                   availableFiles, availableResult, 
                                   allFilesInQueue, file_, i, j, file, result >>

exposeResult(self) == /\ pc[self] = "exposeResult"
                      /\ PrintT(result_[self])
                      /\ j' = [j EXCEPT ![self] = j[self] + 1]
                      /\ pc' = [pc EXCEPT ![self] = "cumputeResults"]
                      /\ UNCHANGED << fileSynchronizedQueue, 
                                      resultsSynchronizedQueue, foundFiles, 
                                      availableFiles, availableResult, 
                                      allFilesInQueue, file_, result_, i, file, 
                                      result >>

master(self) == Search(self) \/ search(self) \/ signalAllFilesInQueue(self)
                   \/ cumputeResults(self) \/ waitResult(self)
                   \/ getResult(self) \/ exposeResult(self)

Read(self) == /\ pc[self] = "Read"
              /\ allFilesInQueue > 0
              /\ allFilesInQueue' = allFilesInQueue - 1
              /\ pc' = [pc EXCEPT ![self] = "take"]
              /\ UNCHANGED << fileSynchronizedQueue, resultsSynchronizedQueue, 
                              foundFiles, availableFiles, availableResult, 
                              file_, result_, i, j, file, result >>

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
              /\ UNCHANGED << resultsSynchronizedQueue, foundFiles, 
                              availableResult, allFilesInQueue, file_, result_, 
                              i, j, result >>

count(self) == /\ pc[self] = "count"
               /\ result' = [result EXCEPT ![self] = "result"]
               /\ pc' = [pc EXCEPT ![self] = "putResult"]
               /\ UNCHANGED << fileSynchronizedQueue, resultsSynchronizedQueue, 
                               foundFiles, availableFiles, availableResult, 
                               allFilesInQueue, file_, result_, i, j, file >>

putResult(self) == /\ pc[self] = "putResult"
                   /\ resultsSynchronizedQueue' = Append(resultsSynchronizedQueue, result[self])
                   /\ availableResult' = availableResult + 1
                   /\ pc' = [pc EXCEPT ![self] = "take"]
                   /\ UNCHANGED << fileSynchronizedQueue, foundFiles, 
                                   availableFiles, allFilesInQueue, file_, 
                                   result_, i, j, file, result >>

worker(self) == Read(self) \/ take(self) \/ count(self) \/ putResult(self)

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
\* Last modified Tue Apr 11 09:58:45 CEST 2023 by filip
\* Created Mon Apr 03 15:43:08 CEST 2023 by filip
