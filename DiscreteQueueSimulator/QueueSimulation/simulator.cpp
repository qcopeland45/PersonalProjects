//
//  simulator.cpp
//  QueueSimulation
//
//  Created by Quincy Copeland on 3/29/19.
//  Copyright © 2019 Quincy Copeland. All rights reserved.
//

#include "simulator.hpp"
#include <iostream>


//Params: Event structs object
//Void: pushes an even into bank priority queue
void Service::addEvent(Event& event) {
    eventQueue.push(event);
}


// add a bank queue customer (not really an event)
void Bank::addBankQueueCustomer(Event& event) {
    bankQueue.push(event);
}

//Params: array of checkers
//Returns: index of checker with the shortest line
int Grocery::chooseShortestLine(std::array<int, 6>& checkers){
    
    int indexOfShortest = 0;
    for (int i = 1; i < checkers.size(); i++) {
        if (checkers[i] < checkers[indexOfShortest]) {
            indexOfShortest = i;
        }
    }
    return indexOfShortest;
}


int Service::getCurentTime() {
    return currentTime;
}

//Bank simulator driver function
void Bank::runSim() {
    while (!eventQueue.empty()) {
        Event customer = eventQueue.top();
        int curTime = getCurentTime();
        curTime = customer.eventTime;
        
        if (curTime > SIMULATION_LEN)
            break;
        
        eventQueue.pop();
        //switch comparing types of events {arrival, departure}
        switch (customer.event) {
            case arrival:
                if (tellersAvailable) {
                    //if there are tellers availble, customer service time should be now (current time)
                    customer.totalServiceTime = customer.serviceDuration;
                    customer.eventTime += customer.serviceDuration;// ??
                    customer.event = departure;
                    addEvent(customer);
                    tellersAvailable--;
                } else {
                    // no tellers available, put customer in bank queue
                    addBankQueueCustomer(customer);
                }
                break;
            case departure:
                if(!bankQueue.empty()) {
                    
                    serviceTimes.push_back(customer.totalServiceTime);
                    Event nextCustomer = bankQueue.front();
                    bankQueue.pop();
                    nextCustomer.totalServiceTime = curTime - nextCustomer.eventTime + nextCustomer.serviceDuration;
                    //serviceTimes.push_back(nextCustomer.totalServiceTime);
                    nextCustomer.eventTime = curTime + customer.serviceDuration;
                    nextCustomer.event = departure;
                    addEvent(nextCustomer);
                } else {
                    serviceTimes.push_back(customer.totalServiceTime);
                    tellersAvailable++;
                }
                break;
            default:
                std::cout << "ERROR: Should never get here! " << std::endl;
        }
    }
}


//Grocery Store simulator driver function
void Grocery::runSim() {
    while (!eventQueue.empty()) {
        Event customer = eventQueue.top();
        // get next event in priority queue
        int curTime = getCurentTime();
        curTime = customer.eventTime;
        
        if (curTime > SIMULATION_LEN)
            break;
        
        eventQueue.pop();
        int shortestLine;
        
        //switch comparing types of events {arrival, departure}
        switch (customer.event) {
            case arrival:
                
                shortestLine = chooseShortestLine(checkers); //finds the line with shoretest service time
                customer.cashier = shortestLine; //cashier refers to an int
                customer.totalServiceTime = checkers.at(shortestLine) + customer.serviceDuration;
                checkers.at(shortestLine)+= customer.serviceDuration;
                customer.eventTime = curTime + checkers.at(shortestLine);
                customer.event = departure;
                addEvent(customer);
                break;
            case departure:
                //customer leaves you need to take away there service time from
                //the total remaining time of the line they are in
                checkers.at(customer.cashier)-= customer.serviceDuration;
                serviceTimes.push_back(customer.totalServiceTime);
                break;
            default:
                std::cout << "ERROR: Should never get here! " << std::endl;
        }
    }
}


