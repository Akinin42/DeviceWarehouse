DELETE FROM models_to_devices;
DELETE FROM phone_models;
DELETE FROM computer_models;
DELETE FROM tvset_models;
DELETE FROM fridge_models;
DELETE FROM vacuum_cleaner_models;
DELETE FROM sizes;
DELETE FROM phones;
DELETE FROM tvsets;
DELETE FROM computers;
DELETE FROM fridges;
DELETE FROM vacuum_cleaners;
INSERT INTO sizes (size_id,length_mm,width_mm,height_mm) VALUES(1,150,75,8);
INSERT INTO sizes (size_id,length_mm,width_mm,height_mm) VALUES(2,550,330,70);
INSERT INTO sizes (size_id,length_mm,width_mm,height_mm) VALUES(3,300,200,16);
INSERT INTO sizes (size_id,length_mm,width_mm,height_mm) VALUES(4,600,600,1670);
INSERT INTO sizes (size_id,length_mm,width_mm,height_mm) VALUES(5,250,350,200);
INSERT INTO phones (device_id,name,country_of_manufacture,company,online_order,installment) VALUES(6,'iPhone4','USA','Apple',true,false);
INSERT INTO phones (device_id,name,country_of_manufacture,company,online_order,installment) VALUES(7,'iPhone5','USA','Apple',true,false);
INSERT INTO phones (device_id,name,country_of_manufacture,company,online_order,installment) VALUES(8,'Galaxy','China','Samsung',true,true);
INSERT INTO phone_models (model_id,name,serial_number,color,size_size_id,cost,availability,memory_in_mb,number_cameras) VALUES(9,'AA-7','8456254-gf','Black',1,22500,true,2048,3);
INSERT INTO phone_models (model_id,name,serial_number,color,size_size_id,cost,availability,memory_in_mb,number_cameras) VALUES(10,'BA-7','8456254-ku','White',1,27500,false,1024,2);
INSERT INTO phone_models (model_id,name,serial_number,color,size_size_id,cost,availability,memory_in_mb,number_cameras) VALUES(11,'AA-22','845fd54-ku','White',1,34500,false,1024,5);
INSERT INTO phone_models (model_id,name,serial_number,color,size_size_id,cost,availability,memory_in_mb,number_cameras) VALUES(12,'BDA-17','845dgf54-ku','Black',1,39500,true,2048,4);
INSERT INTO phone_models (model_id,name,serial_number,color,size_size_id,cost,availability,memory_in_mb,number_cameras) VALUES(13,'AS-1','845fdfd54-ku','White',1,42500,false,1024,3);
INSERT INTO phone_models (model_id,name,serial_number,color,size_size_id,cost,availability,memory_in_mb,number_cameras) VALUES(14,'TT-17','845dfrf54-ku','Black',1,39500,true,2048,2);
INSERT INTO models_to_devices (device_id,model_id) VALUES(6,9);
INSERT INTO models_to_devices (device_id,model_id) VALUES(6,10);
INSERT INTO models_to_devices (device_id,model_id) VALUES(7,11);
INSERT INTO models_to_devices (device_id,model_id) VALUES(7,12);
INSERT INTO models_to_devices (device_id,model_id) VALUES(8,13);
INSERT INTO models_to_devices (device_id,model_id) VALUES(8,14);
INSERT INTO tvsets (device_id,name,country_of_manufacture,company,online_order,installment) VALUES(15,'UE24','China','Samsung',true,false);
INSERT INTO tvsets (device_id,name,country_of_manufacture,company,online_order,installment) VALUES(16,'AA24','China','Samsung',true,false);
INSERT INTO tvsets (device_id,name,country_of_manufacture,company,online_order,installment) VALUES(17,'DD31','China','LG',false,true);
INSERT INTO tvset_models (model_id,name,serial_number,color,size_size_id,cost,availability,category,technology) VALUES(18,'AD2345','45265652-gf','Black',2,34500,true,'first','LED');
INSERT INTO tvset_models (model_id,name,serial_number,color,size_size_id,cost,availability,category,technology) VALUES(19,'PP2345','45568652-gf','White',2,38500,false,'second','TFT');
INSERT INTO tvset_models (model_id,name,serial_number,color,size_size_id,cost,availability,category,technology) VALUES(20,'DF547','85459626-gf','Black',2,67500,false,'first','LED');
INSERT INTO tvset_models (model_id,name,serial_number,color,size_size_id,cost,availability,category,technology) VALUES(21,'AS145','455856522-gf','White',2,76500,true,'second','TFT');
INSERT INTO tvset_models (model_id,name,serial_number,color,size_size_id,cost,availability,category,technology) VALUES(22,'SA147','4526547-gf','Black',2,105500,false,'first','LED');
INSERT INTO tvset_models (model_id,name,serial_number,color,size_size_id,cost,availability,category,technology) VALUES(23,'RE784','45563941-gf','White',2,125500,true,'second','TFT');
INSERT INTO models_to_devices (device_id,model_id) VALUES(15,18);
INSERT INTO models_to_devices (device_id,model_id) VALUES(15,19);
INSERT INTO models_to_devices (device_id,model_id) VALUES(16,20);
INSERT INTO models_to_devices (device_id,model_id) VALUES(16,21);
INSERT INTO models_to_devices (device_id,model_id) VALUES(17,22);
INSERT INTO models_to_devices (device_id,model_id) VALUES(17,23);
INSERT INTO computers (device_id,name,country_of_manufacture,company,online_order,installment) VALUES(24,'MacBook','USA','Apple',true,false);
INSERT INTO computers (device_id,name,country_of_manufacture,company,online_order,installment) VALUES(25,'Laptop11','China','ASUS',true,false);
INSERT INTO computers (device_id,name,country_of_manufacture,company,online_order,installment) VALUES(26,'LaptopZ','China','ASUS',true,false);
INSERT INTO computer_models (model_id,name,serial_number,color,size_size_id,cost,availability,category,processor) VALUES(27,'E345','4577785-gf','Black',3,84500,true,'laptop','atlon');
INSERT INTO computer_models (model_id,name,serial_number,color,size_size_id,cost,availability,category,processor) VALUES(28,'E845','4575486-gf','White',3,88500,false,'notebook','iCore3');
INSERT INTO computer_models (model_id,name,serial_number,color,size_size_id,cost,availability,category,processor) VALUES(29,'SD47','4572354-gf','White',3,87500,false,'notebook','iCore3');
INSERT INTO computer_models (model_id,name,serial_number,color,size_size_id,cost,availability,category,processor) VALUES(30,'SD14','4573565-gf','Black',3,75500,true,'laptop','atlon');
INSERT INTO computer_models (model_id,name,serial_number,color,size_size_id,cost,availability,category,processor) VALUES(31,'DE42','4525685-gf','White',3,69500,true,'laptop','iCore3');
INSERT INTO computer_models (model_id,name,serial_number,color,size_size_id,cost,availability,category,processor) VALUES(32,'AW21','4572565-gf','Black',3,74500,false,'notebook','atlon');
INSERT INTO models_to_devices (device_id,model_id) VALUES(24,27);
INSERT INTO models_to_devices (device_id,model_id) VALUES(24,28);
INSERT INTO models_to_devices (device_id,model_id) VALUES(25,29);
INSERT INTO models_to_devices (device_id,model_id) VALUES(25,30);
INSERT INTO models_to_devices (device_id,model_id) VALUES(26,31);
INSERT INTO models_to_devices (device_id,model_id) VALUES(26,32);
INSERT INTO fridges (device_id,name,country_of_manufacture,company,online_order,installment) VALUES(33,'DSN','Russia','Indesit',true,false);
INSERT INTO fridges (device_id,name,country_of_manufacture,company,online_order,installment) VALUES(34,'DFR','Russia','Indesit',true,false);
INSERT INTO fridges (device_id,name,country_of_manufacture,company,online_order,installment) VALUES(35,'SAR','Russia','LG',true,true);
INSERT INTO fridge_models (model_id,name,serial_number,color,size_size_id,cost,availability,number_of_door,compressor) VALUES(36,'DS456','4576545-gf','Black',4,45500,true,2,'INV');
INSERT INTO fridge_models (model_id,name,serial_number,color,size_size_id,cost,availability,number_of_door,compressor) VALUES(37,'AA456','4556475-gf','White',4,48500,false,1,'CONV');
INSERT INTO fridge_models (model_id,name,serial_number,color,size_size_id,cost,availability,number_of_door,compressor) VALUES(38,'AS436','4545645-gf','Black',4,47500,true,2,'INV');
INSERT INTO fridge_models (model_id,name,serial_number,color,size_size_id,cost,availability,number_of_door,compressor) VALUES(39,'ZX126','4512375-gf','White',4,42500,false,1,'CONV');
INSERT INTO fridge_models (model_id,name,serial_number,color,size_size_id,cost,availability,number_of_door,compressor) VALUES(40,'QW226','4575435-gf','Black',4,41500,true,2,'INV');
INSERT INTO fridge_models (model_id,name,serial_number,color,size_size_id,cost,availability,number_of_door,compressor) VALUES(41,'FG556','4321475-gf','White',4,49500,false,1,'CONV');
INSERT INTO models_to_devices (device_id,model_id) VALUES(33,36);
INSERT INTO models_to_devices (device_id,model_id) VALUES(33,37);
INSERT INTO models_to_devices (device_id,model_id) VALUES(34,38);
INSERT INTO models_to_devices (device_id,model_id) VALUES(34,39);
INSERT INTO models_to_devices (device_id,model_id) VALUES(35,40);
INSERT INTO models_to_devices (device_id,model_id) VALUES(35,41);
INSERT INTO vacuum_cleaners (device_id,name,country_of_manufacture,company,online_order,installment) VALUES(42,'SC41','Russia','Samsung',true,false);
INSERT INTO vacuum_cleaners (device_id,name,country_of_manufacture,company,online_order,installment) VALUES(43,'SC45','Russia','Samsung',true,false);
INSERT INTO vacuum_cleaners (device_id,name,country_of_manufacture,company,online_order,installment) VALUES(44,'LH45','Russia','LG',true,false);
INSERT INTO vacuum_cleaner_models (model_id,name,serial_number,color,size_size_id,cost,availability,amount_litres,number_modes) VALUES(45,'AF456','4576456-gf','Black',5,5600,true,3,2);
INSERT INTO vacuum_cleaner_models (model_id,name,serial_number,color,size_size_id,cost,availability,amount_litres,number_modes) VALUES(46,'AD475','4575689-gf','White',5,7600,false,2,3);
INSERT INTO vacuum_cleaner_models (model_id,name,serial_number,color,size_size_id,cost,availability,amount_litres,number_modes) VALUES(47,'DE456','1236456-gf','Black',5,6600,true,1,2);
INSERT INTO vacuum_cleaner_models (model_id,name,serial_number,color,size_size_id,cost,availability,amount_litres,number_modes) VALUES(48,'GY475','2345689-gf','White',5,8600,false,2,1);
INSERT INTO vacuum_cleaner_models (model_id,name,serial_number,color,size_size_id,cost,availability,amount_litres,number_modes) VALUES(49,'LO456','3426456-gf','Black',5,4600,true,2,1);
INSERT INTO vacuum_cleaner_models (model_id,name,serial_number,color,size_size_id,cost,availability,amount_litres,number_modes) VALUES(50,'SA475','1425689-gf','White',5,3600,false,1,2);
INSERT INTO models_to_devices (device_id,model_id) VALUES(42,45);
INSERT INTO models_to_devices (device_id,model_id) VALUES(42,46);
INSERT INTO models_to_devices (device_id,model_id) VALUES(43,47);
INSERT INTO models_to_devices (device_id,model_id) VALUES(43,48);
INSERT INTO models_to_devices (device_id,model_id) VALUES(44,49);
INSERT INTO models_to_devices (device_id,model_id) VALUES(44,50);