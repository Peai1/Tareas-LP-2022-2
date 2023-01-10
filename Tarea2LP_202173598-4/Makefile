programa: main.o certamen.o
	gcc -o programa main.o certamen.o

main.o: main.c certamen.h
	gcc -c main.c

certamen.o: certamen.c certamen.h
	gcc -c certamen.c
	
clean:
	rm -f programa *.o