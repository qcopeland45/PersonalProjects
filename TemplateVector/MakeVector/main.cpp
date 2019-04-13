//  main.cpp
//  MakeVector
//  Created by Quincy Copeland on 9/10/18.
//  Copyright © 2018 Quincy Copeland. All rights reserved.

#include <iostream>
#include <numeric>
#include "Functions.hpp"
#include "TestFunctions.h"


using namespace std;


void Functions()
{
    
    myVector<int> qVect(10);
    //qVect.setElementValue(5, -99);
    //qVect.pushBack(-99);
    //qVect.setElementValue(4, -101); 
    cout << "element @index 4 " << qVect[4] << endl;
    cout <<"qvect Capcity " << qVect.getCapacity() << endl;
    cout << "qvect size " << qVect.getSize() << endl;
   // assert(qVect.getSize() == 1);
    //assert(qVect.getElementValue(0 == -101));
    //cout << qVect.getElementValue(0) << endl;
    
    
    
    myVector<int> v1(10);
//    myVector<int> v2(15);
//    myVector<float> v3(10);
    myVector<int> v4(15);
//
    v1.pushBack(7);
    v1.pushBack(1);
    v1.pushBack(3);
    v1.pushBack(3);
    
//
//    v2.pushBack(1);
//    v2.pushBack(3);
//
//    v3.pushBack(1.1);
//    v3.pushBack(1.1);
//    v3.pushBack(1.1);
//    v3.pushBack(1.1);
//
    v4.pushBack(1);
    v4.pushBack(1);
    v4.pushBack(3);
    v4.pushBack(3);
    
    if(v1 == v4){
        cout << "v1 == to v4" << endl;
    }
    else {
        cout << "v1 != v4" << endl;
        
    }
    
    if(v1 < v4) {
        cout << "v1 < v4" << endl;
    }
    else {
        cout << "v1 > v4" << endl;
    }
//    v4.pushBack(1.235);
//    v4.pushBack(-1.1);
//    v4.pushBack(4.235);
//    v4.pushBack(2.235);
//
//
//    myVector<string> str(5);
//    myVector<string> str2(5);
//    str.pushBack("hello");
//    str.pushBack("hello");
//    str.pushBack("hello");
//
//    str2.pushBack("hello");
//    str2.pushBack("hello");
//    str2.pushBack("hello1");
//    str2.pushBack("hello");
//
//
//    std::sort(v4.begin(), v4.end());
//
//    for(float x: v4)
//    {
//        cout << "Sorted v4 vector " << x << endl;
//    }
//
//    cout << "Smallest value of v4 Vector " << *std::min_element(v4.begin(), v4.end()) << endl;
//
//    cout << "Last element in v4 vector " << v4.end() << endl;
//    cout << "First element in v4 vector " << v4.begin() << endl;
//    cout << "str size: " << str.getSize() << endl;
//    cout << "str2 size: " << str2.getSize() << endl;
//
//
//    //checking operator functionality
//    if(str == str2)
//    {
//        cout << "str is equal to str2" << endl; //if they are equal
//    }
//    else
//    {
//        cout << "str and str2 are not equal" << endl;
//    }
//
//    if(v1 != v2)
//    {
//        cout << "v1 != to v2" << endl;
//    }
//    else
//    {
//        cout << "v1 is = to v2" << endl;
//    }
//
//    if(v1 >= v2)
//    {
//        cout << "True" << endl; //expecting true
//    }
//    else
//    {
//        cout << "false" << endl;
//    }
//
//    if(v1 > v2)
//    {
//        cout << "True" << endl; //expecting true
//    }
//    else
//    {
//        cout << "false" << endl;
//    }
    
    
//        v2.pushBack(1);
//        v2.pushBack(12);
//        v2.pushBack(10);
//        v2.pushBack(16);
//        v2.pushBack(7);
//        v2.pushBack(3);
//        v2.pushBack(-1);
//        v2.pushBack(45);
//        v2.pushBack(100);
//        v2.pushBack(19);
    
    //v2.sort();
//    int size = v2.getSize();
//    for(int i = 0; i < size; i++)
//    {
//        cout << "v2 " << v2[i] << endl;
//    }
}

int main(int argc, const char * argv[])
{
    
    Functions();
    
    return 0;
}
