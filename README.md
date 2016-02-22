# dna_sequence


GENERAL

The US Government Patent Office's PatentIn program takes an input file and generates
a Sequence Listing File. In some cases, a law firm may not have access to the original
input file but does have the Sequence Listing File. This program is designed to reverse
engineer the Sequence Listing File to reproduce the input file.

The need for this program usually arises when a law firm takes over a case from another
firm, and the previous firm does not hand over the input file to the new firm. This program
is used by a law firm in Seattle in exactly those cases.

The program was designed to be used as a GUI in Windows, where double clicking Run.jar
should cause the program to start.



To run from command line:
$ java ReverseMain

To compile:
$ javac ReversMain.java ReverseSequence.java Sequence.java

