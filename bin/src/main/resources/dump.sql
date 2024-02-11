--
-- PostgreSQL database dump
--

-- Dumped from database version 14.9 (Ubuntu 14.9-0ubuntu0.22.04.1)
-- Dumped by pg_dump version 14.9 (Ubuntu 14.9-0ubuntu0.22.04.1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Data for Name: filme; Type: TABLE DATA; Schema: public; Owner: ti2cc
--

COPY public.filme ("ID_Filme", "Generos", "Atores", "Titutlo", "Classificacao_Indicativa", "Descricao", "Data_Lancamento") FROM stdin;
\.


--
-- Data for Name: usuario; Type: TABLE DATA; Schema: public; Owner: ti2cc
--

COPY public.usuario ("ID_Usuario", "Nome", "Email", "Senha", "Generos", "Data_Nascimento", "Data_Ingresso", "Quantidade_Criticas") FROM stdin;
\.


--
-- Data for Name: resenha; Type: TABLE DATA; Schema: public; Owner: ti2cc
--

COPY public.resenha ("ID_Resenha", "Conteudo", "Data", "ID_Usuario", "ID_Filme", "Opiniao") FROM stdin;
\.


--
-- Data for Name: recomendacao; Type: TABLE DATA; Schema: public; Owner: ti2cc
--

COPY public.recomendacao ("ID_Recomendacao", "Data_Recomendacao", "ID_FIlme", "ID_Resenha", "ID_Usuario") FROM stdin;
\.


--
-- Name: filme_ID_Filme_seq; Type: SEQUENCE SET; Schema: public; Owner: ti2cc
--

SELECT pg_catalog.setval('public."filme_ID_Filme_seq"', 1, false);


--
-- Name: recomendacao_ID_Recomendacao_seq; Type: SEQUENCE SET; Schema: public; Owner: ti2cc
--

SELECT pg_catalog.setval('public."recomendacao_ID_Recomendacao_seq"', 1, false);


--
-- Name: resenha_ID_Resenha_seq; Type: SEQUENCE SET; Schema: public; Owner: ti2cc
--

SELECT pg_catalog.setval('public."resenha_ID_Resenha_seq"', 1, false);


--
-- Name: usuario_id_usuario_seq; Type: SEQUENCE SET; Schema: public; Owner: ti2cc
--

SELECT pg_catalog.setval('public.usuario_id_usuario_seq', 1, false);


--
-- PostgreSQL database dump complete
--

