Для сброса состояния базы данных ( например, для тех случаев, когда необходимо обновить defaultDB.sql) нужно удалить
папку ./db, чтобы изменения вступили в силу при запуске программы.



Готово:

all getById methods
all add methods

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

Комментарий:
- Сделать даты в add и в сущностях (автора и арт объекта) не обязательным параметром, в т.ч. без сторогой проверки на дату
- Id сбивается (вроде во всех случаях добавления данных), если запустить уже один раз запущенную программу.(ответ: Это так работать и должно, в противном случае id был бы не уникален.)
