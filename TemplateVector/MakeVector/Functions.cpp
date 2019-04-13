//  Functions.cpp
//  MakeVector
//  Created by Quincy Copeland on 9/10/18.
//  Copyright © 2018 Quincy Copeland. All rights reserved.

#include "Functions.hpp"
#include <vector>
#include <iostream>

using namespace std;


//CONSTRUCTOR: CREATED WHEN A NEW   myVector object is instantiated.
//myVector::myVector (T initialCapacity)
//{
//    cout << "Constructor" << endl; //making sure code is running this functions
//    capacity = initialCapacity;
//    arr = new int[capacity];
//    size = 0;
//}

/*********************************************************/
//COPY CONSTRUCTOR.
//myVector::myVector(const myVector& originalVect)
//{
//    arr = new int[originalVect.capacity];
//
//    for(int i = 0; i < originalVect.size; i++)
//    {
//        arr[i] = originalVect.arr[i];
//    }
//
//    capacity = originalVect.capacity;
//    size = originalVect.size;
//}

/*********************************************************/
//void myVector::pushBack(int value)
//{
//    arr[size] = value;
//    size++;
////    vect.arr[vect.size] = value;
////    vect.size++;
//}

/*********************************************************/
//void myVector::popBack()
//{
//    arr[size] = 0;
//    size--;
//}

/*********************************************************/
//int myVector::getElementValue(int indexValue)
//{
//    return arr[indexValue]; // not working
//}

/*********************************************************/
//void myVector::setElementValue(int index, int newVal)
//{
//    arr[index] = newVal;
//}

/*********************************************************/
//void myVector::growVector()
//{
//    int *newVector = new int(capacity * 2); // creating new vector
//
//    for(int i = 0; i <= size - 1; i++)
//    {
//        setElementValue(i, arr[i]); //setting newValue
//    }
//
//    arr = newVector;
//    capacity *= 2;
//}

/*********************************************************/
//OVERLOADED  = OPERATOR
//myVector& myVector::operator=(const myVector& rhs)
//{
//    cout << "Op overloaded" << endl; //making sure code is running this functions
//    capacity = rhs.capacity;
//    arr = rhs.arr;
//    size = rhs.size;
//    return *this;
//}

/*********************************************************/
//
//int& myVector::operator[](int rhsIndex)
//{
//    return arr[rhsIndex];
//}

/*********************************************************/
//DESTRUCTOR
//myVector::~myVector()
//{
//    delete []arr;
//    arr = nullptr;
//    capacity = 0;
//    size = 0;
//}

/*********************************************************/
//int myVector::getSize()
//{
//    return size;
//}

/*********************************************************/
//int myVector::getCapacity()
//{
//    return capacity;
//}

