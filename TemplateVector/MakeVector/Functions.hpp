//  Functions.hpp
//  MakeVector
//  Created by Quincy Copeland on 9/10/18.
//  Copyright © 2018 Quincy Copeland. All rights reserved.

#ifndef Functions_hpp
#define Functions_hpp

#include <stdio.h>
#include <vector>
#include <iostream>
#include <algorithm>

using namespace std;

template <typename T>
class myVector {

private:
    
    T *arr;
    int capacity;
    int size;
    
public:
    
myVector (int initialCapacity)
{
    cout << "Constructor" << endl; //making sure code is running this functions
    capacity = initialCapacity;
    arr = new T[capacity];
    size = 0;
}
    
T* begin(){return arr;}

const T* begin()const {return arr;}

T* end() {return arr + size;}

const T* end()const {return arr + size;}


//copy constructor
myVector(const myVector& originalVect)
{
    arr = new T[originalVect.capacity];
    
    for(int i = 0; i < originalVect.size; i++)
    {
        arr[i] = originalVect.arr[i];
    }
    
    capacity = originalVect.capacity;
    size = originalVect.size;
}
    
//push back method
void pushBack(T value)
{
    cout << "PushBack" << endl;
    arr[size] = value;
    size++;
}
    
//pop back method
void popBack()
{
    cout << "PopBack" << endl;
    arr[size] = 0;
    size--;
}
    
//get element at index
int getElementValue(int indexValue)
{
    return arr[indexValue]; // not working
}

//set element value at index
void setElementValue(int index, int newVal)
{
    
    arr[index] = newVal;
}
    
//grow vector
void growVector()
{
    int *newVector = new int(capacity * 2); // creating new vector
    
    for(int i = 0; i <= size - 1; i++)
    {
        setElementValue(i, arr[i]); //setting newValue
    }
    
    arr = newVector;
    capacity *= 2;
}
    
//overloaded = operator
myVector& operator=(const myVector& rhs)
{
    cout << "Op overloaded" << endl; //making sure code is running this functions
    capacity = rhs.capacity;
    arr = rhs.arr;
    size = rhs.size;
    return *this;
}
    
//return value at index
T& operator[](int rhsIndex)
{
    return arr[rhsIndex];
}
    
//sort method
void sort()
{
    //selection sort
    for(int i = 1; i <= size; i++)
    {
       T temp = arr[i];
        for(int j = i; j < size; j++)
        {
            if(temp > arr[j])
            {
                temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
    }
}
    
~myVector()
{
    delete []arr;
    arr = nullptr;
    capacity = 0;
    size = 0;
}
//get size method
int getSize()
{
    return size;
}
//get capacity of vector
int getCapacity()
{
    return capacity;
}
    
//equal operator
bool operator==(myVector& rhs)
{
    if(size != rhs.size)
        return false;
    
    for(int i = 0; i < size; i++) {
        if(arr[i] != rhs.arr[i])
            return false;
    }
    return true;
}
    
bool operator!=(myVector& rhs)
{
    //return *this == rhs.size; not sure how to return with one line
    if(*this == rhs)
    {
        return false;
    }
    return true;
}

    
bool operator<(myVector& rhs) {
    
    if(size < rhs.size)
    {
        return true;
    }
    
    int smallestSize = 0;
    if(size > rhs.size)
    {
        smallestSize = rhs.size;
    }
    else
    {
        smallestSize = size;
    }
    
    for(int i = 0; i < smallestSize; i++)
    {
        if(arr[i] > rhs.arr[i])
        {
            return false;
        }
    }
   
    return false;
}
    
    
bool operator<=(myVector& rhs)
{
    if(*this > rhs.size)
    {
        return false;
    }
    return true;
}

bool operator>=(myVector& rhs)
{
    //return *this < rhs.size; how to return in one line??
    if(*this < rhs)
    {
        return true;
    }
    return false;
}

bool operator>(myVector& rhs)
{
    return size > rhs.size;
//    if(size > rhs.size)
//    {
//        return true;
//    }
//    return false;
}


    
};





#endif /* Functions_hpp */
