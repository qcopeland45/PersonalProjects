//  main.cpp
//  DeckOfCards
//  Created by Quincy Copeland on 8/28/18.
//  Copyright © 2018 Quincy Copeland. All rights reserved.

#include <iostream>
#include "CardStruct.hpp"
#include <vector>
#include <string>
#include <cstdlib>

//inline comments are mainly for myself to help solidify my understanding of my own code (sorry if it looks sloppy <^_^<) )

//Function helper to run all my other function code
void runFunctions()
{
    int flushCount = 0;
    int straightCount = 0;
    int royalCount = 0;
    int fullHouseCount = 0;
    
    std::vector<Cards> deckOfCards = createDeck();
    for(int i = 0; i < 10000; i++)
    {
        shuffleDeck(deckOfCards);
        bool flush = isFlush(deckOfCards);
        bool straight = isStraight(deckOfCards);
        bool royalFlush = isRoyalFlush(deckOfCards);
        bool fullHouse = isFullHouse(deckOfCards);
    
        if(royalFlush){
            royalCount++;
        }
        else if(fullHouse){
            fullHouseCount++;
        }
        else if(straight){
            straightCount++;
        }
        else if(flush){
            flushCount++;
        }
    }

    std::cout << "Royal Flush Count: " << royalCount << std::endl;
    std::cout << "Full House: " << fullHouseCount << std::endl;
    std::cout << "Straight Count: " << straightCount << std::endl;
    std::cout << "Flush Count: " << flushCount << std::endl;
    
    /*printDeck(deckOfCards);
    shuffleDeck(deckOfCards);
    printDeck(deckOfCards);
    bool hand = isFlush(deckOfCards);
    std::cout << hand << std::endl;
    bool straight = isStraight(deckOfCards);
    std::cout << straight << std::endl;
    bool straightFlush = isStraightFlush(deckOfCards);
    std::cout << straightFlush << std::endl;*/
}

int main(int argc, const char * argv[])
{
    srand ((int)time(NULL));
    runFunctions();
    //std::vector<Cards> cardDeck = createDeck();
    //printDeck(cardDeck);
    //shuffleDeck(cardDeck);
//    printDeck(cardDeck);
    
    
    
    //call to a functions that runs my other functions
    /*std::vector<Cards> v1;
    v1.push_back({"spades" , 1});
    v1.push_back({"spades" , 4});
    v1.push_back({"spades" , 2});
    v1.push_back({"spades" , 3});
    v1.push_back({"spades" , 5});
    
    
    printDeck(v1);
    isFlush(v1);
    isStraight(v1);
    //selectionSort(v1);
    printDeck(v1);
    
    
    std::cout << isFlush(v1) << std::endl; //working
    std::cout << isStraight(v1) << std::endl;
    
    std::vector<Cards> v2;
    v2.push_back({"spades" , 14});
    v2.push_back({"hearts" , 14});
    v2.push_back({"spades" , 12});
    v2.push_back({"spades" , 11});
    v2.push_back({"spades" , 10});
    
    //bool test = isFlush(v2);
    //std::cout << test << std::endl;
    
    //int testMinCardValue = minCard(v2);
    //std::cout << testMinCardValue << std::endl;
    //std::cout<<isRoyalFlush(v2);*/
    
    
    
    
    return 0;
}
