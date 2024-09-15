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
