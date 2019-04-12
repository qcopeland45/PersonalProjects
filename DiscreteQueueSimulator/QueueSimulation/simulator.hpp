//
//  simulator.hpp
//  QueueSimulation
//
//  Created by Quincy Copeland on 3/29/19.
//  Copyright © 2019 Quincy Copeland. All rights reserved.
//

#ifndef simulator_hpp
#define simulator_hpp

#include <iostream>
#include <string>
#include <vector>
#include <queue>
#include <array>
#include <unordered_map>

#define NUM_TELLERS 6
#define SIMULATION_LEN 43200 // seconds in 12 hours day

enum EventType {arrival, departure};

//Event Struct
struct Event {
    
    //member variables
    double eventTime;
    double serviceDuration;
    double totalServiceTime;
    int customerID;
    int cashier;
    EventType event;
};


struct compareEventTime {
    bool operator()(const Event& lhs, const Event& rhs) const {
        return lhs.eventTime > rhs.eventTime;
    }
};


//******************BASE CLASS***************//
class Service {
    private:
        //std::priority_queue<Event, std::vector<Event>, compareEventTime> eventQueue;
        int currentTime = 0;
    public:
        void addEvent(Event& event);
        int getCurentTime();
        std::vector<int> serviceTimes;
        std::priority_queue<Event, std::vector<Event>, compareEventTime> eventQueue;
    
};


//************Bank Class*****************//
class Bank : public Service {
    
    private:
        //member variables
        std::queue<Event> bankQueue;
        int tellersAvailable = NUM_TELLERS;
    
    public:
        //methods
        void addBankQueueCustomer(Event& event);
        void runSim();
    
        //public member variable
        std::vector<int> serviceTimes;
};


//************Grocery Class*****************//
class Grocery : public Service {
    private:
        //member variables
        std::array<int, 6> checkers;
    public:
        //methods
        int chooseShortestLine(std::array<int, 6>& checkers);
        void runSim();
    
        //public member variable
        std::vector<int> serviceTimes;
};












#endif /* simulator_hpp */
