JFLAGS = -g
JC = javac
JVM = java
.SUFFIXES: .java .class
.java.class: 
	$(JC) $*.java

CLASSES = / Node.java / FibonacciHeap.java / hashtagcounter.java 

default: classes

classes: $(CLASSES:.java=.class)

clean: 
	$(RM) *.class