﻿--
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
INSERT INTO user (username, password, name, first_lastname, second_lastname, city, followed) VALUES
   ('viewer', '$2a$10$UaIX1wXSdM58WtMqcF9LC.kjvNGQHACJdcpttgP9yiA/U6GBganJS', 'Viewer', 'Viewer1', 'Viewer2', 'city', 'juan');

INSERT INTO advertisement (title, description, date, userA) VALUES

   ('anuncio1' ,'1 anuncio', parsedatetime('15-09-2020 18:47:52.69', 'dd-MM-yyyy hh:mm:ss.SS'), 1),

   ('anuncio2' ,'2 anuncio', parsedatetime('16-09-2020 17:47:52.69', 'dd-MM-yyyy hh:mm:ss.SS'), 1),
   ('anuncio3' ,'3 anuncio', parsedatetime('15-09-2020 19:47:52.69', 'dd-MM-yyyy hh:mm:ss.SS'), 1);
