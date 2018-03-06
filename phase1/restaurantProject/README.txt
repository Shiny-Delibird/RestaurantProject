The event structure will be like:

Server/Cook doing event | Type of Event | Order that Event is happening on | Notes?

Event types will be:

takeOrder
cookConfirmOrder
cookFinishedOrder
tableReceivedOrder
tableRejectedOrder
tableRequestedBill

Notes will have either order info or reason why order was rejected

ex:

server1 | takeOrder |  | 4 ; Burger x 2 +lettuce, fries x 1

cook1 | cookConfirmOrder | 2 |