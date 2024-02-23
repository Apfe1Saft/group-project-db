Для сброса состояния базы данных ( например, для тех случаев, когда необходимо обновить defaultDB.sql) нужно удалить
папку ./db, чтобы изменения вступили в силу при запуске программы.



Готово:

all getById

Необходимо сделать:

addLocation
addOwner

updateLocation
updateOwner
updateAuthor
updateEvent
updatePurchase

removeLocation
removeOwner
removeAuthor
removeEvent
removePurchase

get{1}By{2}Filter()
{1} - entity name
{2} - value name ( Id, Name, Description... )

clear() - чистка всей базы данных