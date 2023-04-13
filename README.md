# SourceTracker

SourceTracker is a tool for have an overview of the size of a Java project.
It spot all the Java sources and realize a ranking of the top N longest sources.
I also display the distribution of the files in base of their size.
There are two version available, one has a CLI interface and the other has a simple GUI.

## Technical details

The tool is implemented with concurrent programming, using Threads and shared buffers.
A full report is available at /doc/.
