CodePath Final Project - README
===

# SPOTLIGHT

## Table of Contents
1. [Overview](#Overview)
1. [Product Spec](#Product-Spec)
1. [Wireframes](#Wireframes)
2. [Schema](#Schema)

## Overview
### Description 
An App to promote events and sale tickets. Planners can post event on and have control on how many tickets are being sold on the platform and can validate purchased tickets on the event day. Simple users can view events on the platform and make reservations by buying or not.

### App Evaluation
- **Category:**
    Entertainment, marketing, promotional and Social
- **Mobile:**
    Available only on android device for now.
- **Story:**
    Spotlight is designed to promote events in any category and type, will sale tickets and give Planner access to post their events, get access to a dashboard to visualize data on sold tickets and can validate purchased tickets on the events day.
- **Market:**
    People of any age and gender, from every social category, who use smart devices and want to go to events  but would like to purchase tickets on their smart device in their home or office comfort or elsewhere.
- **Habit:** This app could be used as often as users want to check out on new events or get information on how many events  available to participate to. 
- **Scope:** First, we want to give a better way both to event planners to expose their events and event custommers to know about events and make reservations easier. Afterwards, perhaps we will be able to put more information about  artists and where events will be hosted introducing google map. We will also continue to make it easier for users and planners to navigate and get more advantage using Spotlight.

## Product Spec

### 1. User Stories (Required and Optional)

**Required Must-have Stories**

 - [x] User can see events without creating account.
 - [x] User can select events to get more information about.
 - [x] User can create an account to buy tickets for events
 - [x] User can select a profile picture
 - [x] User can edit personal informations
 - [x] User can logout
 - [x] Planner can post event (Validated by Spotlight team before posted in the timeline)
 - [x] Planner can login into his account
 - [x] Planner can add event by category
 - [x] Planner can have two accounts(One as admin, one as user )
 - [ ] User can search for events by date
 - [ ] User can buy tickets for events they are interested in.
 - [ ] After purchase, user can receive a validation code ( SMS, email)
 - [ ] Planner can see in real time number of sold tickets.
 - [ ] Planner can validate sold ticket at event day from participants.


**Optional Nice-to-have Stories**

 - [ ] After purchase user receive a validation code ( Barcode QR Code)
 - [ ] User can rate events
 - [ ] User can serach for events by artist
 - [ ] User can serach for events by category
 - [ ] User can search for events by location


### 2. Screen Archetypes
   *Users*
   * Home(No login)
        *Allow user to see events without having to login or have an account
   * Event Details(No login)
        *User can see details of a event he clicked on
   * Main Page
        *User can either login or register
   * Login Page
        *User login into his account
   * Register Page
        *User creates an account and is redirected to the login page to connect himself
   * Home
        *User can see events this time with a drawer activity giving many other options
   * Settings
        *User can edit his personal infos and choose a profile picture
   * Event Details
        *User can add events to Cart to pay for later
   * Cart
        *User can see events that have been added to cart and proceed to next step
   * Payment Page
        *User can pay using credit card then is redirected to feed and cart is empty
   * BarCode Page
        * User can see the barcode generated which will be scanned by the planner at the event
   
   *Admins*
   * Home(No login)
        *Admin can see events but must login to post a event
   * Event Details(No login)
        *Admin can see details about events but must login to post a event
   * Main Page
        *Admin can login as a user register as a user or login as an admin/planner
   * Login Admin page
        *Admin login into his account
   * Category
        *Admin choose what category of event he is posting
   * Add event page
        *Admin choose a picture to represent the event, a description, the name of the event and the price of the ticket

### 3. Navigation

**Drawer Navigation** (Drawer to Screen)

* Buy
* Orders
* Category
* Settings
* Logout

**Flow Navigation** (Screen to Screen)
* Buy -> Jumps to Cart
* Orders -> Jumps to Orders
* Category -> Jumps to Events Category
* Settings -> Text Field to be modified and photo to be uploaded

## Wireframes
[Add picture of your hand sketched wireframes in this section]
<img src="https://github.com/Vizyone/Spotlight/blob/origin/master/IMG_20190909_232249.jpg" width=600>



### [BONUS] Digital Wireframes & Mockups
<img src="https://github.com/Vizyone/Spotlight/blob/origin/master/Screen1.PNG" width=150>
<img src="https://github.com/Vizyone/Spotlight/blob/origin/master/Screen2.PNG" width=150>
<img src="https://github.com/Vizyone/Spotlight/blob/origin/master/Screen3.PNG" width=150>
<img src="https://github.com/Vizyone/Spotlight/blob/origin/master/Screen4.PNG" width=150>
<img src="https://github.com/Vizyone/Spotlight/blob/origin/master/Screen5.PNG" width=150>
<img src="https://github.com/Vizyone/Spotlight/blob/origin/master/Screen6.PNG" width=150>
<img src="https://github.com/Vizyone/Spotlight/blob/origin/master/Screen7.PNG" width=150>
<img src="https://github.com/Vizyone/Spotlight/blob/origin/master/Screen8.PNG" width=150>
<img src="https://github.com/Vizyone/Spotlight/blob/origin/master/Screen9.PNG" width=150>

### [BONUS] Interactive Prototype
https://www.figma.com/proto/o6oHJX8blt3DEfXCuj1W3X/Spotlight?node-id=48%3A2&scaling=scale-down
<img src="Interactive Prototype Wireframe.gif" width=300 height=300> 

## Schema 
[This section will be completed in Unit 9]
### Models
#### Users

   | Property      | Type     | Description |
   | ------------- | -------- | ------------|
   | name          | String   | name of the user|
   | phone         | String   | phone of the user |
   | image         | File     | user's profile image |
   | password      | String   | password of the user |
   | email         | String   | email adress of the user |
   
#### Events

   | Property      | Type     | Description |
   | ------------- | -------- | ------------|
   | ename         | String   | name of the event|
   | description   | String   | description of the event|
   | price         | Number   | price of the event|
   | image         | File     | poster of the event| 
   | category      | String   | Category of the event|
   | pid           | String   | a unique id for the event|
   | date          | Datetime | date the event was posted at| 
   | time          | Datetime | time the event was posted at|
   
#### Cart

   | Property      | Type     | Description |
   | ------------- | -------- | ------------|
   | ename         | String   | name of the event|
   | quantity      | Number   | number of tickets|
   | price         | Number   | price of the event|
   | pid           | Number   | id of the event added to cart| 
### Networking
- [Add list of network requests by screen ]
- Home Feed Screen
        - (Read/GET) Query all events
- Event Details Screen
        - (Read/GET) Query post user clicked on
- Login Admin/ User Screen
        - (Read/GET) Check if user exists to connect him
- Register User
        - (Create/POST) Create a new account
- Settings Activity
        - (Update/PUT) Update user profile image
        - (Update/PUT) Update user infos
- Cart Screen
        - (Read/GET) Query all events added to Cart
        - (Update/PUT) Edit an event that has beem added to Cart
        - (Delete) Delete an event that has been added to Cart
- Add Event Screen 
        - (Create/POST) Post a new event
- [OPTIONAL: List endpoints if using existing API such as Yelp]
