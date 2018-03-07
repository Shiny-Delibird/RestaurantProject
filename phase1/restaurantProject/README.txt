=============EVENTS==========
The event structure in the event.txt file is:

Server/Cook doing event | Type of Event | Order that Event is happening on | Notes

Event types will be:

takeOrder           -- notes has order details
cookConfirmOrder
cookFinishedOrder
tableReceivedOrder
tableRejectedOrder  -- notes has why order was rejected
tableRequestedBill
receiveShipment     -- notes has shipment info

Notes will contain misc. info, like order details for example

ex:

server1 | takeOrder |  | 4 ; Burger x 2 +lettuce, fries x 1

cook1 | cookConfirmOrder | 2 |

 | receiveShipment |  | bread x 2, eggs x 12, milk x 5

=============MINIMUMS===========
The minimums file determines what the minimum amount of each ingredient should be before reordering

The file is structured in the format ingredient | quantity

If the file minimums.txt does not exist, it generates the file with default minimums of 10 for each item in the
inventory (this is also the case with an incomplete minimums file with missing entries)

NOTE: the minimums can be changed directly by altering the minimums.txt file. PLEASE ONLY INSERT NON-NEGATIVE INTEGERS

================REORDERING============
Requests for more ingredients are auto-generated and stored in the requests.txt file as ingredients are used.

Each line is structured in the format ingredient x quantity. WARNING: the default quantity ordered is 20 and it is up to
the user to change it if the default is insufficient.

When a new shipment of ingredients is processed, the requests.txt file is wiped and any ingredients still under the
minimum threshold are then added back to requests.txt