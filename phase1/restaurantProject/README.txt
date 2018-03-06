The event structure will be like:

Server/Cook doing event | Type of Event | Order that Event is happening on | Notes?

Event types will be:

takeOrder           -- notes has order details
cookConfirmOrder
cookFinishedOrder
tableReceivedOrder
tableRejectedOrder  -- notes has why order was rejected
tableRequestedBill
receiveShipment     -- notes has shipment info

Notes will have misc. info, like order details for example

ex:

server1 | takeOrder |  | 4 ; Burger x 2 +lettuce, fries x 1

cook1 | cookConfirmOrder | 2 |

 | receiveShipment |  | bread x 2, eggs x 12