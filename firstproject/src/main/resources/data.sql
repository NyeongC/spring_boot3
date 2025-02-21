--INSERT INTO article(title, content) values ('가가가가', '1111');
--INSERT INTO article(title, content) values ('나나나나', '2222');
--INSERT INTO article(title, content) values ('다다다다', '3333');

INSERT INTO member(email, password) values ('cjfsud23@naver.com', '1111');
INSERT INTO member(email, password) values ('cnchoi@naver.com', '2222');
INSERT INTO member(email, password) values ('kdtoaj14@naver.com', '3333');

-- article 테이블에 데이터 추가
INSERT INTO article(title, content) VALUES('당신의 인생 영화는?', '댓글 고');
INSERT INTO article(title, content) VALUES('당신의 소울 푸드는?', '댓글 고고');
INSERT INTO article(title, content) VALUES('당신의 취미는?', '댓글 고고고');
--INSERT INTO article(title, content) VALUES('좋아하는 스포츠는?', '댓글 고고고');

-- 1번 게시글의 댓글 추가
INSERT INTO comment(article_id, nickname, body) VALUES(1, 'Park', '굿 윌 헌팅');
INSERT INTO comment(article_id, nickname, body) VALUES(1, 'Kim', '아이 엠 샘');
INSERT INTO comment(article_id, nickname, body) VALUES(1, 'Choi', '쇼생크 탈출');

-- 2번 게시글의 댓글 추가
INSERT INTO comment(article_id, nickname, body) VALUES(2, 'Park', '치킨');
INSERT INTO comment(article_id, nickname, body) VALUES(2, 'Kim', '샤브샤브');
INSERT INTO comment(article_id, nickname, body) VALUES(2, 'Choi', '초밥');

-- 3번 게시글의 댓글 추가
INSERT INTO comment(article_id, nickname, body) VALUES(3, 'Park', '조깅');
INSERT INTO comment(article_id, nickname, body) VALUES(3, 'Kim', '유튜브 시청');
INSERT INTO comment(article_id, nickname, body) VALUES(3, 'Choi', '독서');