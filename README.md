# Programming-II
CPSC 233 Code from the University of Calgary

Battleship Game Project 

Developed both a text and GUI based Battleship game a team of 5 using Java

Constructed the project using objected-oriented programming and inheritance principles while meeting deadlines and presenting weekly demos of current progress

*Ensure all 3 folders, lit.jpg and wave.jpg, are in the same directory.*


To compile: 

javac control/*.java

javac logic/*.java

javac gui/*.java


To run:

java control/Controller

java control/GamePlay


To test:

Compile: 

javac -cp .:junit-4.12.jar:hamcrest-core-1.3.jar tests/*.java


To run:

java -cp .:junit-4.12.jar:hamcrest-core-1.3.jar org.junit.runner.JUnitCore tests.ComputerTest

java -cp .:junit-4.12.jar:hamcrest-core-1.3.jar org.junit.runner.JUnitCore tests.ShipTest

java -cp .:junit-4.12.jar:hamcrest-core-1.3.jar org.junit.runner.JUnitCore tests.HumanTest

java -cp .:junit-4.12.jar:hamcrest-core-1.3.jar org.junit.runner.JUnitCore tests.BoardTest
