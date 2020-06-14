## Glovo Planning Poker Project
## Software Requirements Specification
### 1.0
     
    06/06/2020
`**Made by 
  Grafi01234**`
  
  _Template from Guru99 course_
  
#1. Introduction 

The Glovo Planning Poker project aims to enable voting in Sprints.

##1.1 Purpose
The Purpose of this document is to outline the requirements for https://glovo-planning-poker.herokuapp.com/ website . This document will be used by all stakeholders including developers and testers.

##**1.2 Scope**
The scope of this project is limited to the testing of the features described in the succeeding sections of this document.
Non-functional testing like stress,performance has a low significance.
Automatic testing is recommended
Functional testing & external interfaces  is recommended.
The Poker Planning site will be compatible with Chrome version 83.

#**2. Specific Requirements**
The Planning Poker will have 1 role:
**User**
Following features/modules will be available for user:

+ Creating random Session
+ Creating Named Session
+ Joining other user Session
+ Add name
+ Spectator
+ Add Ticket
+ Clear
+ Show Votes
+ Share Session link
+ Voting
+ Median
+ Average
+ All Votes

Description of the modules

| **Module Name**                | **Description**                                                                                                       |
|----------------------------|-------------------------------------------------------------------------------------------------------------------|
| Creating random Session    | The ability to create a session with a randomly generated name                                                    |
| Creating Named Session     | Possibility to create session with id with any chosen name                                                        |
| Joining other user Session | After entering the same session name as every other user and clicking Join, the player will join the shared page. |
| Add name                   | Adding a name results in displaying it in voting results                                                          |
| Spectator                  | Spectator should only be able to follow the vote without taking part                                              |
| Add Ticket                 | The name of the Ticket should be changed in real time to recognize what users are voting for                      |
| Clear                      | The ticket and votes should disappear
| Share Session link         | The mechanism of copying the current URL                                                                          |

#**3. Front End Details**
This section describes the Front end of Glovo Planning Poker site 

**Main site**
+ Session ID text field
+ Join button
+ Create random button

**Planning Poker site**
+ Your name text field
+ Spectator? Check box
+ Ticket text box
+ Clear button
+ Show votes button
+ Share session link button
+ Current vote
+ Median table
+ Average table
+ 0.5 , 1,  2, 3, 5, 8, ∞, ? **voting buttons**
+ All votes table
##**3.1 Technical Requirements**
**Session ID:**

+ **T1** Session ID - Session ID is required if not random session

**Your name:**

+ **T2**    Your name – Your name cannot be the same as other user in same session
+ **T3**    Your name – Your name must not be blank for voting
+ **T4**    Your name - Your name cannot have ‘ : ‘ symbol

**Spectator? :**
+ **T5**    Spectator - Spector when checked block voting function
**Share session link:**
+ **T6**    Share session link - Share session link should copy actual session id
**Median:**
+ **T7**    Median - Median should return the median of all votes
**Average:**
+ **T8**    Average - Average should return the average of all votes
**Voting buttons**
+ **T9**    Voting buttons - Voting buttons should return the same value as on the button.
+ **T1**0  Voting buttons - Voting buttons should be accessible to non-spectator
**All votes:**
+ **T11**  All votes - All votes table should show participants and their votes in a vote
+ **T12**  All votes - All votes table should not show Spectators voices.
**Clear**
+ **T13** Clear button - should purge ticket name
+ **T14** Clear button - should leave "..." in the summary of voting next to the name

##3.2 Non-Functional Requirements
+ It is able to process 1000 requests / s
+ Can handle up to 100 users sessions

##3.3 Design Constraints
Site should be simple and easy to use, it should be easily accessible and perform its function quickly.

#5. Change Management Process
Changes to the SRS either from the development, testing team or the client side will be communicated to the Jezorko.

Any change made to the SRS will require a sign off  Jezorko.

Once approved changed will be made to the SRS and the new SRS will be circulated to all stakeholders