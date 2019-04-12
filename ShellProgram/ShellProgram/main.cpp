//
//  main.cpp
//  ShellProgram
//
//  Created by Quincy Copeland on 1/31/19.
//  Copyright © 2019 Quincy Copeland. All rights reserved.
//

#include <iostream>
#include <vector>
#include <string>
#include <cstring>
#include <unistd.h>
#include <sys/wait.h>
#include "shelpers.hpp"
#include <map>


using namespace std;

int main(int argc, const char * argv[])
{
    cout << "QUINCY'S SHELL PROGRAM\n";
    vector<string> argument;
    string line = "";

    //waiting to get commands from shell prompt
    while (getline(cin, line)) {
        
        vector<pid_t> pids;
        pid_t child_pid;
        argument = tokenize(line);
        vector<Command> commands = getCommands(argument);

        for (int i = 0; i < commands.size(); i++) {
            //checking if we need to change the directory
            if (commands[0].exec == "cd") {
                const char *path;
                if (commands.at(0).argv.at(1) != nullptr) {
                    path = commands.at(0).argv.at(1);
                }
                else {
                    path = getenv("HOME");
                }
                if (chdir(path) == -1) { //changing directory
                    perror("cd error");
                }
                continue;
            }
            
            //custom variable
            size_t myVar = argument[0].find("=");
            if (myVar != string::npos){
                string varName = argument[0].substr(0, myVar);
                string value = argument[0].substr(myVar + 1, argument[0].length() - 1);
                
                if (argument[0].at(myVar + 1) == '"'){
                    cout << "quotes detected\n";
                    for (int q = 1; q < argument.size(); q++) {
                        value+= " ";
                        value+= argument[q];
                    }
                    
                    value = value.substr(1, value.size() -2);
                }
                if ((setenv(varName.c_str(),value.c_str(), 1) == - 1)) { //setting environment variable
                    perror("setenv");
                }
            }
        
            
            
            //printing the value of an assigned variable
            if (argument[0] == "echo") {
                for (int k = 1; k < argument.size(); k++){
                    if (argument[k].at(0) == '$'){
                        char *variable;
                        if ((variable = getenv(argument[k].substr(1, argument[k].length() - 1).c_str()))==NULL) {
                            perror("getenv");
                        }
                        argument[k] = variable;
                    }
                }
            }
            
            //error check that process was created properly
            if ((child_pid = fork()) == -1) {
                perror("fork error");
                exit(1);
            }

            //if 0, its the current process aka the CHILD PROCESS!!
            if (child_pid == 0) {
                if (dup2(commands[i].fdStdout, 1) == -1) {
                    perror("error with dup2");
                    exit(-1);
                }

                if (dup2(commands[i].fdStdin, 0) == -1) {
                    perror("error with dup2");
                    exit(-1);
                }

                for (int j = 0; j < commands.size(); j++) {
                    if (commands[j].fdStdin != 0) {
                        close(commands[j].fdStdin);
                    }
                    if (commands[j].fdStdout != 1) {
                        close(commands[j].fdStdout);
                    }
                }
                execvp(commands[i].argv[0], const_cast<char* const*>(commands[i].argv.data()));
            }
            //PARENT PROCESS WAITS
            else {
                pids.push_back(child_pid);
            }
        }
        //closing shell processes
        for (int j = 0; j < commands.size(); j++) {
            if (commands[j].fdStdin != 0) {
                close(commands[j].fdStdin);
            }
            if (commands[j].fdStdout != 1) {
                close(commands[j].fdStdout);
            }
        }

        for (const auto& pid : pids) {
            waitpid(pid, NULL, 0);
        }
    }

    
    return 0;
}
