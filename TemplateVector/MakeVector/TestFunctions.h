//
//  Header.h
//  MakeVector
//
//  Created by Quincy Copeland on 9/13/18.
//  Copyright © 2018 Quincy Copeland. All rights reserved.
//

#ifndef Header_h
#define Header_h
void vectorFunctions()
{
    
    myVector<int> v1(10);
    myVector<int> v2(15);
    v1.pushBack(1);
    v1.pushBack(1);
    
    v2.pushBack(1);
    v2.pushBack(1);
    
        if(v1 < v2)
        {
            cout << "True" << endl;
        }
        else
        {
            cout << "false" << endl;
        }
    
//    v2.pushBack(1);
//    v2.pushBack(12);
//    v2.pushBack(10);
//    v2.pushBack(16);
//    v2.pushBack(7);
//    v2.pushBack(3);
//    v2.pushBack(-1);
//    v2.pushBack(45);
//    v2.pushBack(100);
//    v2.pushBack(19);
    
    v2.sort();
    int size = v2.getSize();
    for(int i = 0; i < size; i++)
    {
        cout << v2[i] << endl;
    }
    
    
    
//    if(v1 == v2)
//    {
//        cout << "True" << endl;
//    }
//    else
//    {
//        cout << "false" << endl;
//    }
    
//    if(v1 != v2)
//    {
//        cout << "Yes not equal: True" << endl;
//    }
//    else
//    {
//        cout << "No are equal: false" << endl;
//    }
    
//    v1.pushBack(5);
//    v1.pushBack(-2);
//    v1.pushBack(3);
//    v1.pushBack(-99);
//    v1.pushBack(1);
//    v1.pushBack(0);
//
//    cout <<"Vector size: " << v1.getSize() << endl;
//    v1.popBack();
//    cout <<"Vector size after popBack: " << v1.getSize() << endl;
//
//
//    myVector<string> vstring(3);
//    vstring.pushBack("hello");
//    vstring.pushBack("hello world");
//    vstring.pushBack("hello jurasic world");
//
//    //printing each charcter in the vector
//    cout << vstring[2] << endl;
//    int i = 0;
//    for(char c:vstring[1])
//    {
//        cout << "vstring char at index "<< i << ' ' << c << endl;
//        i++;
//    }
    
    
    
}
#endif /* Header_h */
