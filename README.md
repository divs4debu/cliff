## This project aims at building cache library for images. 

### Library Module: 

#### Cliff : Aims at User level interface for get user input for url, operators

#### Arguments : Hold the value input by the user like url, context, view

#### Processor: This interface is used when you need to do a process like manipulate bitmaps, process user provided arguments.
This provides flexibility in adding more processing units. Also multiple processors can work in conjection.

Note: The part of bitmap manipulation is not done right now but this is how i was going to done: 
    Crop
    - Create a CropProcessor class that takes bitmap as argument and produces bitmap as result
    - CropProcessor class will have view and enum [fitXY, cropToPadding, center..]
    - Override process method to implement crop functionality and return a processed bitmap
    Using this ideology we could build multiple image processors. Then create mutliple methods to accept those processors from user.
    Then ArgumentProcessor can process argument and invoke respective processors to perform task.

#### Factories : The library contains various factories
    - NetworkClientFactory
    - CacheProvider 
    These are used in order to build dependcies also give user to implement these and provide to library inorder to replace default.
    
    To acheive this we need make methods in argument that accept these factories and argument processor that utilizes these factories then 
    can propogate these factories to other classes.
    
#### DiskLruCache: This library uses JakeWharton's DiskLruCache for storing data on disk. But this can be easily replaced by user provided factory.

Note: There are some redundant code that could be removed and some code not written at all like, get image urls from server.
This is the poc of the library.   