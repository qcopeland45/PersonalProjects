//  CardStruct.cpp
//  DeckOfCards
//  Created by Quincy Copeland on 8/28/18.
//  Copyright © 2018 Quincy Copeland. All rights reserved.

#include "CardStruct.hpp"


//Function purpose:         creating deck of cards
//Function paramaters:      vector from user defined card struct
//pre-conditions:           n/a
//post-conditions:          n/a
std::vector<Cards> createDeck()
{
    const int CARD_RANGE = 14; //range of values in a deck of cards
    std::vector<Cards> deck; //creates a vector of cards called deck to be returned
    
    for(int i = 2; i <= CARD_RANGE; i++)
    {
        for(std::string suit:{"spades","clubs","hearts","diamonds"}) //since we know the collection it's easy to hard code
        {
            deck.push_back({suit, i});
        }
    }
    return deck;
}

//Function purpose:         printing a deck of cards
//Function paramaters:      vector from user defined card struct
//pre-conditions:           must be a vector
//post-conditions:          n/a
void printDeck(std::vector<Cards>const& cardDeck) //pass by const reference
{
    std::string rankArr[4] = {"ace of ", "jack of ", "queen of ", "king of "}; //somewhat of an extra line of code 
    for(Cards cards: cardDeck)
    {
        if(cards.rank == 14){
            std::cout << rankArr[0] + cards.suit << std::endl;
        }
        else if(cards.rank == 11){
            std::cout << rankArr[1] + cards.suit << std::endl;
        }
        else if(cards.rank == 12){
            std::cout << rankArr[2] + cards.suit << std::endl;
        }
        else if(cards.rank == 13){
            std::cout << rankArr[3] + cards.suit << std::endl;
        }
        else
        std::cout << cards.rank << " of " << cards.suit << std::endl;
    }
}

void shuffleDeck(std::vector<Cards>& cardDeck)
{
    const int SIZE_OF_DECK = 52;
    for(int i = SIZE_OF_DECK - 1; i > 0; i--)
    {
        int j = std::rand() % i;
        Cards temp = cardDeck[i];
        cardDeck[i] = cardDeck[j];
        cardDeck[j] = temp;
    }
}

bool isFlush(std::vector<Cards>const& cardDeck)
{
    int firstFive = 5;
    for(int i = firstFive -1; i > 0; i--)
    {
        if(cardDeck[i].suit != cardDeck[i - 1].suit)
        {
            return false;
        }
    }
    return true;
}

bool isStraight(std::vector<Cards>& cardDeck) //something seems off about this
{
    //sort(cardDeck);
    int firstFive = 5;
    std::vector<Cards> hand = {cardDeck[0],cardDeck[1],cardDeck[2],cardDeck[3],cardDeck[4]};
    selectionSort(hand);
    for(int i = 0; i < firstFive; i++)
    {
        if(hand[i].rank + 1 != hand[i + 1].rank)// check this line
        {
            return false;
        }
    }
    return true;
}

bool isStraightFlush(std::vector<Cards>& cardDeck)
{
    return isStraight(cardDeck) && isFlush(cardDeck); //if those are both true should return true
}
//bubble sort
void sort(std::vector<Cards>& deck)
{
    int sortLen = 5;
    for(int i = 0; i < sortLen; i++)
    {
        for(int j = i + 1; j < sortLen; j++)
        {
            if(deck[i].rank > deck[j].rank)
            {
                Cards temp = deck[i];
                deck[i] = deck[j];
                deck[j] = temp;
            }
        }
    }
}
//selection sort
void selectionSort(std::vector<Cards>& deck) //sort hand
{
    int firstFive = 5;
    for(int i = 0; i < firstFive - 1; i++)
    {
        int j = minIndex(deck, i);
        Cards temp = deck[i];
        deck[i] = deck[j];
        deck[j] = temp;
    }
}

int minIndex(std::vector<Cards>& deck, int num) //looking for 10
{
    int minimum = num;
    int firstFive = 5;
    for(int i = num + 1; i < firstFive; i++)
    {
        if(deck[minimum].rank > deck[i].rank)
        {
            minimum = i;
        }
    }
    return minimum;
}

bool isRoyalFlush(std::vector<Cards>& deck)//i never get a royal flush
{
    std::vector<Cards> hand = {deck[0],deck[1],deck[2],deck[3],deck[4],deck[5]};
    int min = minCard(hand);
    return isStraightFlush(deck) && min == 10;
}

bool isFullHouse(std::vector<Cards>& deck)
{
    int firstFive = 5;
    int count1 = 0;
    int count2 = 0;
    int rank1 = deck[0].rank;
    int rank2 = 0;
    count1 = count(deck, rank1);
    
    for(int i = 1; i < firstFive; i++)
    {
        if(rank1 != deck[i].rank)
        {
            rank2 = deck[i].rank;
            break;
        }
    }
    count2 = count(deck, rank2);
    return count1 + count2 == 5 && count1 != 4 && count2 != 4;
}

int count(std::vector<Cards>& deck, int lookFor)
{
    int firstFive = 5;
    int count = 0;
    for(int i = 0; i < firstFive; i++)
    {
        if(deck[i].rank == lookFor)
        {
            count++;
        }
    }
    return count;
}

int minCard(std::vector<Cards>& deck) //looking for 10
{
    int minimum = deck[0].rank;
    int firstFive = 5;
    for(int i = 1; i < firstFive; i++)
    {
        if(minimum > deck[i].rank)
        {
            minimum = deck[i].rank;
        }
    }
    return minimum;
}








