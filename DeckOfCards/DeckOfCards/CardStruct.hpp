//  CardStruct.hpp
//  DeckOfCards
//  Created by Quincy Copeland on 8/28/18.
//  Copyright © 2018 Quincy Copeland. All rights reserved.

#ifndef CardStruct_hpp
#define CardStruct_hpp

#include <stdio.h>
#include <vector>
#include <string>
#include <iostream>
#include <cstdlib>


//struct
struct Cards{
    std::string suit;
    int rank;
};

std::vector<Cards> createDeck();

void printDeck(std::vector<Cards>const&  cardDeck);

void shuffleDeck(std::vector<Cards>& cardDeck);

bool isFlush(std::vector<Cards>const& cardDeck);

bool isStraight(std::vector<Cards>& cardDeck);

bool isStraightFlush(std::vector<Cards>& deck);

void sort(std::vector<Cards>& deck);

void selectionSort(std::vector<Cards>& deck);

bool isRoyalFlush(std::vector<Cards>& deck);

bool isFullHouse(std::vector<Cards>& deck);

//bool Contains(std::vector<Cards>& cardDeck, int lookFor);

int count(std::vector<Cards>& deck, int lookFor);

int minCard(std::vector<Cards>& deck);

int minIndex(std::vector<Cards>& deck, int num);

//bool isStraight

#endif /* CardStruct_hpp */
