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
INSERT INTO users (login, password, name, first_surname, second_surname, city) VALUES
   ('viewer', '$2a$10$UaIX1wXSdM58WtMqcF9LC.kjvNGQHACJdcpttgP9yiA/U6GBganJS', 'name1', 'first_surname1', 'second_surname1', 'city1'),
   ('login2', 'pass2', 'name2', 'first_surname2', 'second_surname2', 'city2'),
   ('login3', 'pass3', 'name3', 'first_surname3', 'second_surname3', 'city3');

INSERT INTO advertisement (title, description, image, userA) VALUES
   ('anuncio1' ,'1 anuncio', 'data:image/jpeg;base64,/9j', 'juan1'),
   ('anuncio2' ,'2 anuncio', 'data:image/jpeg;base64,/9j', 'juan2'),
   ('anuncio3' ,'3 anuncio', 'data:image/jpeg;base64,/9j', 'juan3');


INSERT INTO example_entities (name) VALUES
   ('entity_01'),
   ('entity_02'),
   ('entity_03'),
   ('entity_04'),
   ('entity_05'),
   ('entity_06'),
   ('entity_07'),
   ('entity_08'),
   ('entity_09'),
   ('entity_10'),
   ('entity_11'),
   ('entity_12'),
   ('entity_13'),
   ('entity_14'),
   ('entity_15'),
   ('entity_16'),
   ('entity_17'),
   ('entity_18'),
   ('entity_19'),
   ('entity_20'),
   ('entity_21'),
   ('entity_22'),
   ('entity_23'),
   ('entity_24'),
   ('entity_25'),
   ('entity_26'),
   ('entity_27'),
   ('entity_28'),
   ('entity_29'),
   ('entity_30');
