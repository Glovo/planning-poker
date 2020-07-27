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
+ Spectator Mode
+ Add Ticket
+ Clear
+ Show Votes
+ Share Session link
+ Voting (0.5, 1, 2, 3, 5, 8, ∞, ?)
+ Median
+ Average
+ All Votes table

Description of the modules

| **Module Name**            | **Description**                                                                                                   |
|----------------------------|-------------------------------------------------------------------------------------------------------------------|
| Creating random Session    | The ability to create a session with a randomly generated name                                                    |
| Creating Named Session     | Possibility to create session with id with any chosen name                                                        |
| Joining other user Session | After entering the same session name as every other user and clicking Join, the player will join the shared page. |
| Add name                   | Adding a name results in displaying it in voting results                                                          |
| Spectator Mode             | Spectator should only be able to follow the vote without taking part                                              |
| Add Ticket                 | The name of the Ticket should be changed in real time to recognize what users are voting for                      |
| Clear                      | The ticket and votes should disappear                                                                             |
| Share Session link         | The mechanism of copying the current session id                                                                   |
| Show Votes                 | The game user can check the current votes                                                                         |
| Voting                     | User can vote witch values : 0.5, 1, 2, 3, 5, 8. Also he can choose ∞ and ?.                                      |
| Median                     | The medina is shown based on the numerical votes of the players.                                                  |
| Average                    | The average is calculated from the numerical votes of the players.                                                |
| All votes                  | The scoreboard represents voters and their votes                                                                  |

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

+ **T1** Session ID - Special character colon is not allowed.

**Your name:**

+ **T2**    Your name – Your name cannot be the same as other user in same session
+ **T3**    Your name – Your name must not be blank for voting
+ **T4**    Your name - Special character colon is not allowed.

**Spectator? :**
+ **T5**    Spectator - Player name must not be empty should appear when clicking spec without name.

**Share session link:**
+ **T6**    Share session link - After clicking button change to : Link copied!

**Median:**
+ **T7**    Median - Only shows after voting witch numeric.
+ **T8**    Median - Only shows when at least one player have name, is voting and he`s not in spectator mode.

**Average:**
+ **T9**    Average - Only shows after voting witch numeric.
+ **T10**   Average - Only shows when at least one player have name, is voting and he`s not in spectator mode.

**Voting buttons**
+ **T11**    Voting buttons - They should not work in Spectator mode.
+ **T12**    Voting buttons - They should not work with empty Your name text field.

**Ticket**
+ **T13**    Ticket - Special character colon is not allowed.

**All votes:**
+ **T14**  All votes - Only shows when at least one player have name and he`s not in spectator mode.
+ **T15**  All votes - table should not show Spectators voices.

**Clear**
+ **T16** Clear button - should purge ticket name
+ **T17** Clear button - should leave "..." in the summary of voting next to the name

**ShowVotes**
+ **T18** Show votes - Name must be given.

##3.2 Non-Functional Requirements
+ It is able to process 1000 requests / s
+ Can handle up to 100 users sessions

##3.3 Design Constraints
Site should be simple and easy to use, it should be easily accessible and perform its function quickly.

#4. Change Management Process
Changes to the SRS either from the development, testing team or the client side will be communicated to the Jezorko.

Any change made to the SRS will require a sign off  Jezorko.

Once approved changed will be made to the SRS and the new SRS will be circulated to all stakeholders