--
--  The MIT License (MIT)
--
--  Copyright (c) 2017-2019 Bernardo Martínez Garrido
--  
--  Permission is hereby granted, free of charge, to any person obtaining a copy
--  of this software and associated documentation files (the "Software"), to deal
--  in the Software without restriction, including without limitation the rights
--  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
--  copies of the Software, and to permit persons to whom the Software is
--  furnished to do so, subject to the following conditions:
--  
--  The above copyright notice and this permission notice shall be included in all
--  copies or substantial portions of the Software.
--  
--  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
--  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
--  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
--  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
--  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
--  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
--  SOFTWARE.
--


-- ****************************************
-- This SQL script populates the initial data.
-- ****************************************
INSERT INTO user (username, password, name, first_lastname, second_lastname, city) VALUES
   ('viewer', '$2a$10$UaIX1wXSdM58WtMqcF9LC.kjvNGQHACJdcpttgP9yiA/U6GBganJS', 'Viewer', 'Viewer1', 'Viewer2', 'city'),
   ('viewer2', '$2a$10$OazefPSDK0KyGuosq/0PoeFwyuraVQyewMbWNmxYAwU1k25h5gsGe', 'viewer2', 'viewer2', 'viewer2', 'city2'),
   ('viewer3', '$2a$10$TI0OXicCwUpWeYZ7yrE9AOHZUdAtdVGd97p1z/bcXnnNqPuCm4O5u', 'viewer3', 'viewer3', 'viewer3', 'city3');

INSERT INTO advertisement (title, description, date, price, userA, isOnHold) VALUES
   ('anuncio1' ,'primer anuncio', parsedatetime('15-09-2020 18:47:52.69', 'dd-MM-yyyy hh:mm:ss.SS'), 5.00, 1, 1),
   ('anuncio2' ,'segundo anuncio', parsedatetime('16-09-2020 17:47:52.69', 'dd-MM-yyyy hh:mm:ss.SS'), 10.00, 1, 0),
   ('anuncio3' ,'tercer anuncio', parsedatetime('15-09-2020 19:47:52.69', 'dd-MM-yyyy hh:mm:ss.SS'), 100.00, 1, 0),
   ('anuncio4' ,'cuarto anuncio', parsedatetime('15-09-2020 18:47:52.69', 'dd-MM-yyyy hh:mm:ss.SS'), 50.00, 2, 0),
   ('anuncio5' ,'quinto anuncio', parsedatetime('16-09-2020 17:47:52.69', 'dd-MM-yyyy hh:mm:ss.SS'), 15.00, 2, 0),
   ('anuncio6' ,'sexto anuncio', parsedatetime('15-09-2020 19:47:52.69', 'dd-MM-yyyy hh:mm:ss.SS'), 20.00, 3, 0);

INSERT INTO likes (user, adLiked) VALUES
	(1,1);
   
INSERT INTO userFollowed(userId, followedUser) VALUES
	(1, 'viewer2'),
	(1, 'viewer3'),
	(3, 'viewer');

INSERT INTO messages(text, sender, receiver, date, seen) VALUES
	('hola', 1, 2, parsedatetime('15-09-2020 18:47:52.69', 'dd-MM-yyyy hh:mm:ss.SS'), true),
	('hola, que tal?', 2, 1, parsedatetime('15-09-2020 19:47:52.69', 'dd-MM-yyyy hh:mm:ss.SS'), true),
	('bien y tu?', 1, 2, parsedatetime('15-09-2020 20:47:52.69', 'dd-MM-yyyy hh:mm:ss.SS'), true),
	('bien', 2, 1, parsedatetime('15-09-2020 21:47:52.69', 'dd-MM-yyyy hh:mm:ss.SS'), true);
