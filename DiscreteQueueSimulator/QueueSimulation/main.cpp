//
//  main.cpp
//  QueueSimulation
//
//  Created by Quincy Copeland on 3/29/19.
//  Copyright © 2019 Quincy Copeland. All rights reserved.
//

#include <stdio.h>
#include <iostream>
#include <queue>
#include <stdlib.h>
#include <array>
#include <iomanip>

#include "simulator.hpp"

//double printPercentiles (std::vector<int> &serviceTimes, std::string name){
//    sort(serviceTimes.begin(),serviceTimes.end());
//
//    int service10thIndex = .1*serviceTimes.size();
//    int service50thIndex = .5*serviceTimes.size();
//    int service90thIndex = .9*serviceTimes.size();

//    double service10th = (double) serviceTimes.at(service10thIndex)/60;
//    double service50th = (double) serviceTimes.at(service50thIndex)/60;
//    double service90th = (double) serviceTimes.at(service90thIndex)/60;
//
//    return service90th;
//}



void printPercentiles (std::vector<int> &serviceTimes, std::string name) {

    sort(serviceTimes.begin(),serviceTimes.end());

    int service10thIndex = .1*serviceTimes.size();
    int service50thIndex = .5*serviceTimes.size();
    int service90thIndex = .9*serviceTimes.size();


    double service10th = (double) serviceTimes.at(service10thIndex)/60;
    double service50th = (double) serviceTimes.at(service50thIndex)/60;
    double service90th = (double) serviceTimes.at(service90thIndex)/60;

    std::cout <<std::fixed<<std::setprecision(2)<<name<<" service times in minutes:\n"<<" 10th %tile: "<<service10th<<"\n"<<" 50th %tile: "<<service50th<<"\n"<<" 90th %tile: "<<service90th<<"\n"<<std::endl;


}


int main(int argc, const char * argv[]) {
    
    //arugment validation
    if (argc != 4) {
        std::cerr<< "invalid number of arguments program ending..\n";
        std::cout << "Usage ex:  ' 0.3', '10.3 ', '2345 ' \n";
        exit(-1);
    }
    
    int const operation_hours = 12;
    int const total_minutes = operation_hours * 60;
    int const total_seconds = total_minutes * 60;
    
    //get command line args
    int totalCustomers = std::atof(argv[1]) * total_minutes;
    int processTime = std::atof(argv[2]) * 60;
    int seed = std::atoi(argv[3]);
    std::srand(seed);
    
//    std::cout << totalCustomers << std::endl;
//    std::cout << processTime << std::endl;
//    std::cout << total_seconds << std::endl;
    

//    std::cout << "Teller array size " << teller.size() << std::endl;
//    std::cout << "Cheker array size " << checker.size() << std::endl;
    
    
    
    //std::cout << "total customer: " << totalCustomers << std::endl;
    
    Bank bankSim;
    Grocery grocerySim;
    //event loop creation, adding events to queues
    for (int i = 0; i < totalCustomers; i++) {
        Event event;
        int arival = rand() % total_seconds;
        int duration = rand() % processTime;
        event.eventTime = arival;
        event.serviceDuration = duration;
        event.customerID = i;
        bankSim.addEvent(event);
        grocerySim.addEvent(event);
    }
    
    bankSim.runSim();
    grocerySim.runSim();
    
    //print precentile information
    printPercentiles(bankSim.serviceTimes,"BANK");
    printPercentiles(grocerySim.serviceTimes, "GROCERY");
    
//    double bankTime = printPercentiles(bankSim.serviceTimes, "Bank");
//    double storeTime = printPercentiles(grocerySim.serviceTimes,"Supermarket");
//
//    std::cout<<std::fixed << std::setprecision(2)<< bankTime <<"\t" << storeTime << std::endl;
    
    
    return 0;

}

