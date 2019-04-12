make:
#compile and link main.cpp and simulator.cpp into simulator
	clang++ -std=c++17 -fsanitize=address -o simulator QueueSimulation/main.cpp QueueSimulation/simulator.cpp 

run:
	./simulator $(arg1) $(arg2) $(arg3)
	 #echo argument is $(argument)

test:
	chmod +x test.sh
	./test.sh