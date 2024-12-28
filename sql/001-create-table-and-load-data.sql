DROP TABLE IF EXISTS pokemon;

CREATE TABLE pokemon (
 id int unsigned AUTO_INCREMENT,
 name VARCHAR(20) NOT NULL,
 type1 VARCHAR(20) NOT NULL,
 type2 VARCHAR(20) NOT NULL,
 PRIMARY KEY(id)
);

INSERT INTO pokemon (name,type1,type2) VALUES ('キモリ','くさ','');
INSERT INTO pokemon (name,type1,type2) VALUES ('ジュプトル','くさ','');
INSERT INTO pokemon (name,type1,type2) VALUES ('ジュカイン','くさ','');
INSERT INTO pokemon (name,type1,type2) VALUES ('アチャモ','ほのお','');
INSERT INTO pokemon (name,type1,type2) VALUES ('ワカシャモ','ほのお','かくとう');
INSERT INTO pokemon (name,type1,type2) VALUES ('バシャーモ','ほのお','かくとう');
INSERT INTO pokemon (name,type1,type2) VALUES ('ミズゴロウ','みず','');
INSERT INTO pokemon (name,type1,type2) VALUES ('ヌマクロー','みず','じめん');
INSERT INTO pokemon (name,type1,type2) VALUES ('ラグラージ','みず','じめん');


DROP TABLE IF EXISTS TypeList;

CREATE TABLE TypeList (
 id int unsigned AUTO_INCREMENT,
 type VARCHAR(20) NOT NULL,
 PRIMARY KEY(id)
);

INSERT INTO TypeList (type) VALUES ('ノーマル');
INSERT INTO TypeList (type) VALUES ('ほのお');
INSERT INTO TypeList (type) VALUES ('みず');
INSERT INTO TypeList (type) VALUES ('でんき');
INSERT INTO TypeList (type) VALUES ('くさ');
INSERT INTO TypeList (type) VALUES ('こおり');
INSERT INTO TypeList (type) VALUES ('かくとう');
INSERT INTO TypeList (type) VALUES ('どく');
INSERT INTO TypeList (type) VALUES ('じめん');
INSERT INTO TypeList (type) VALUES ('ひこう');
INSERT INTO TypeList (type) VALUES ('エスパー');
INSERT INTO TypeList (type) VALUES ('むし');
INSERT INTO TypeList (type) VALUES ('いわ');
INSERT INTO TypeList (type) VALUES ('ゴースト');
INSERT INTO TypeList (type) VALUES ('ドラゴン');
INSERT INTO TypeList (type) VALUES ('あく');
INSERT INTO TypeList (type) VALUES ('はがね');
INSERT INTO TypeList (type) VALUES ('フェアリー');
