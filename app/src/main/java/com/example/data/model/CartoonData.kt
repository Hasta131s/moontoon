package com.example.data.model

object CartoonData {
    fun getShows(): List<CartoonShow> = listOf(
        CartoonShow(
            title = "Adventure Time",
            searchKeyword = "Adventure Time",
            genres = listOf("#aksiyon", "#fantastik", "#macera"),
            description = "İnsan Finn ve esnek sihirli köpek Jake'in Ooo Ülkesi'ndeki efsanevi maceraları.",
            rating = "8.6",
            years = "2010-2018",
            seasonsCount = 10,
            episodes = listOf(
                Episode("Adalar Bölüm 1", "https://ti-content-global.cdn.turner.com/PROD-APAC/C_ADVTME_XXXXX823_MEI_TUR/543e58cd-bc3d-4887-954d-293af49db442/PCTV.m3u8", "https://ti-content-global.cdn.turner.com/PROD-APAC/C_ADVTME_XXXXX823_MEI_TUR/C_ADVTME_XXXXX823_MEI_TUR_VIDSCREENSHOT.jpg", 1, 1),
                Episode("150 Dakikalık Özel Bölüm", "https://ti-content-global.cdn.turner.com/PROD-APAC/C_ADVTME_XXXXX654_MEI_TUR/d7006e33-8ca1-428f-a1b7-0304de9216ab/PCTV.m3u8", "https://ti-content-global.cdn.turner.com/PROD-APAC/C_ADVTME_XXXXX654_MEI_TUR/C_ADVTME_XXXXX654_MEI_TUR_VIDSCREENSHOT.jpg", 1, 2),
                Episode("Finn'in En İyi 5 Sahnesi", "https://ti-content-global.cdn.turner.com/PROD-APAC/C_ADVTME_XXXXX210_MEI_TUR/f6bb1789-5976-4085-b7c5-2ea7c39c0476/PCTV.m3u8", "https://ti-content-global.cdn.turner.com/PROD-APAC/C_ADVTME_XXXXX210_MEI_TUR/C_ADVTME_XXXXX210_MEI_TUR_VIDSCREENSHOT.jpg", 1, 3),
                Episode("Marceline'in Babası", "https://ti-content-global.cdn.turner.com/PROD-APAC/C_ADVTME_0XXXX067_REC_TR/763b2ada-66ec-4568-8309-5b9e09e3c96f/PCTV.m3u8", "https://ti-content-global.cdn.turner.com/PROD-APAC/C_ADVTME_0XXXX067_REC_TR/C_ADVTME_0XXXX067_REC_TR_VIDSCREENSHOT.jpg", 1, 4),
                Episode("Bebek Jack", "https://ti-content-global.cdn.turner.com/PROD-APAC/C_ADVTME_0XXXX068_REC_TR/ef30ba05-74da-4da3-b23c-ae418a4ee722/PCTV.m3u8", "https://ti-content-global.cdn.turner.com/PROD-APAC/C_ADVTME_0XXXX068_REC_TR/C_ADVTME_0XXXX068_REC_TR_VIDSCREENSHOT.jpg", 1, 5),
                Episode("Sezon 5, Bölüm 48: Betty", "https://ti-content-global.cdn.turner.com/PROD-APAC/C_ADVTME_05048604_MET_TUR/c735f704-cea6-433a-b4cf-2227c5a8d011/PCTV.m3u8", "https://ti-content-global.cdn.turner.com/PROD-APAC/C_ADVTME_05048604_MET_TUR/C_ADVTME_05048604_MET_TUR_VIDSCREENSHOT.jpg", 5, 48),
                Episode("Sezon 5, Bölüm 14: Simon ve Marcy", "https://ti-content-global.cdn.turner.com/PROD-APAC/C_ADVTME_05014578_MET_TUR/94fedabe-dd58-4b51-a887-ca1ee815b5d2/PCTV.m3u8", "https://ti-content-global.cdn.turner.com/PROD-APAC/C_ADVTME_05014578_MET_TUR/C_ADVTME_05014578_MET_TUR_VIDSCREENSHOT.jpg", 5, 14),
                Episode("Sezon 7, Bölüm 5: Futbol", "https://wme-gep-avs-replacement-cms-prod-assets.wme-digital.com/encodings/TR_SA_ADVTME_0207_01_COMP_e8e71bcedb.mp4/manifest.mpd", "https://wme-gep-avs-replacement-cms-prod-assets.wme-digital.com/TR_SA_ADVTME_0207_01_COMP_336804ccb7.jpg", 7, 5)
            )
        ),
        CartoonShow(
            title = "Regular Show (Sürekli Dizi)",
            searchKeyword = "Regular Show",
            genres = listOf("#komedi", "#macera", "#gençlik"),
            description = "Parkta çalışan Mordecai adında mavi bir alakarga ve Rigby adında bir rakunun absürt, sürreal komik hikayeleri.",
            rating = "8.5",
            years = "2010-2017",
            seasonsCount = 8,
            episodes = listOf(
                Episode("Alfa Kubbe", "https://ti-content-global.cdn.turner.com/PROD-APAC/C_REGSHO_XXXXX225_MEI_TUR/22597bbd-ae6b-47e4-b8cc-64b38503556d/PCTV.m3u8", "https://ti-content-global.cdn.turner.com/PROD-APAC/C_REGSHO_XXXXX225_MEI_TUR/C_REGSHO_XXXXX225_MEI_TUR_VIDSCREENSHOT.jpg", 1, 1),
                Episode("Zaman Döngüsü", "https://ti-content-global.cdn.turner.com/PROD-APAC/C_REGSHO_0XXXX0476_REC_TR/74dee910-da06-4c6c-884e-fdf0148a7133/PCTV.m3u8", "https://ti-content-global.cdn.turner.com/PROD-APAC/C_REGSHO_0XXXX0476_REC_TR/C_REGSHO_0XXXX0476_REC_TR_VIDSCREENSHOT.jpg", 1, 2),
                Episode("Uzay Yarışı", "https://ti-content-global.cdn.turner.com/PROD-APAC/C_REGSHO_0XXXX0481_REC_TR/dc42d393-5fd6-4093-bc9c-ee2a40d5224e/PCTV.m3u8", "https://ti-content-global.cdn.turner.com/PROD-APAC/C_REGSHO_0XXXX0481_REC_TR/C_REGSHO_0XXXX0481_REC_TR_VIDSCREENSHOT.jpg", 1, 3),
                Episode("Sezon 4, Bölüm 31: Golf Kulübü", "https://ti-content-global.cdn.turner.com/PROD-APAC/C_REGSHO_04031445_MET_TUR/54010ea6-373c-4d8e-a7cc-0400bdb18fde/PCTV.m3u8", "https://ti-content-global.cdn.turner.com/PROD-APAC/C_REGSHO_04031445_MET_TUR/C_REGSHO_04031445_MET_TUR_VIDSCREENSHOT.jpg", 4, 31),
                Episode("Sezon 4, Bölüm 13: Sandviç", "https://ti-content-global.cdn.turner.com/PROD-APAC/C_REGSHO_04013427_MET_TUR/77b17b8a-25a9-4e1b-822b-87ab8d1c1316/PCTV.m3u8", "https://ti-content-global.cdn.turner.com/PROD-APAC/C_REGSHO_04013427_MET_TUR/C_REGSHO_04013427_MET_TUR_VIDSCREENSHOT.jpg", 4, 13)
            )
        ),
        CartoonShow(
            title = "The Amazing World of Gumball (Gumball)",
            searchKeyword = "The Amazing World of Gumball",
            genres = listOf("#komedi", "#aile", "#okul"),
            description = "Elmore kasabasında yaşayan mavi kedi Gumball ve onun evlatlık japon balığı kardeşi Darwin'in komik, eğlenceli ve sıra dışı okul hayatı.",
            rating = "8.3",
            years = "2011-2019",
            seasonsCount = 6,
            episodes = listOf(
                Episode("100 Dakikalık Gumball Özel", "https://ti-content-global.cdn.turner.com/PROD-APAC/C_GUMBAL_XXXXX205_MEI_TUR/c77b5891-bd60-45ca-b540-3c998f50a2d0/PCTV.m3u8", "https://ti-content-global.cdn.turner.com/PROD-APAC/C_GUMBAL_XXXXX205_MEI_TUR/C_GUMBAL_XXXXX205_MEI_TUR_VIDSCREENSHOT.jpg", 1, 1),
                Episode("Temiz Ol, Havalı Ol", "https://ti-content-global.cdn.turner.com/PROD-APAC/C_GUMBAL_XXXXX185_MEI_TUR/f7fe8381-4ee5-4e78-b731-47f388526be9/PCTV.m3u8", "https://ti-content-global.cdn.turner.com/PROD-APAC/C_GUMBAL_XXXXX185_MEI_TUR/C_GUMBAL_XXXXX185_MEI_TUR_VIDSCREENSHOT.jpg", 1, 2),
                Episode("Öfke", "https://ti-content-global.cdn.turner.com/PROD-APAC/C_GUMBAL_0XXXX0247_REC_TR/961abaa2-2075-4b23-ade3-27be989b6772/PCTV.m3u8", "https://cn.i.cdn.ti-platform.com/content/335/ofke/video/tr/ofke.33ae30d6.jpg", 1, 3),
                Episode("Kuklalar", "https://ti-content-global.cdn.turner.com/PROD-APAC/C_GUMBAL_0XXXX0262_REC_TR/06c7f37d-1db8-4d9f-b07d-3e7f9dc190dc/PCTV.m3u8", "https://ti-content-global.cdn.turner.com/PROD-APAC/C_GUMBAL_0XXXX0262_REC_TR/C_GUMBAL_0XXXX0262_REC_TR_VIDSCREENSHOT.jpg", 1, 4),
                Episode("Sezon 2, Bölüm 37: İnternet", "https://ti-content-global.cdn.turner.com/PROD-APAC/C_GUMBAL_02037556_MET_TUR/eb82a49d-1caf-4c0f-970e-64a8aa383e59/PCTV.m3u8", "https://ti-content-global.cdn.turner.com/PROD-APAC/C_GUMBAL_02037556_MET_TUR/C_GUMBAL_02037556_MET_TUR_VIDSCREENSHOT.jpg", 2, 37)
            )
        ),
        CartoonShow(
            title = "Ben 10",
            searchKeyword = "Ben 10",
            genres = listOf("#aksiyon", "#uzaylı", "#macera"),
            description = "Eski bir saatin (Omnitrix) gücüyle 10 farklı uzaylı kahramana dönüşebilen 10 yaşındaki Ben Tennyson'ın maceraları.",
            rating = "7.5",
            years = "2016-2021",
            seasonsCount = 4,
            episodes = listOf(
                Episode("Sürprizlere Bayılırım", "https://ti-content-global.cdn.turner.com/PROD-APAC/C_BEN160_0XXXX0638_REC_TR/27f64bcc-92ce-4bba-8bfc-2589278b1af9/PCTV.m3u8", "https://ti-content-global.cdn.turner.com/PROD-APAC/C_BEN160_0XXXX0638_REC_TR/C_BEN160_0XXXX0638_REC_TR_VIDSCREENSHOT.jpg", 1, 1),
                Episode("Su Tankı", "https://ti-content-global.cdn.turner.com/PROD-APAC/C_BEN160_0XXXX0109_REC_TR/b764045c-8711-40dc-aeeb-95d683ed4da9/PCTV.m3u8", "https://ti-content-global.cdn.turner.com/PROD-APAC/C_BEN160_0XXXX0109_REC_TR/C_BEN160_0XXXX0109_REC_TR_VIDSCREENSHOT.jpg", 1, 2),
                Episode("Elmas Kafa", "https://ti-content-global.cdn.turner.com/PROD-APAC/C_BEN160_0XXXX0105_REC_TR/2798c846-c8c5-46a7-8444-416bd974db38/PCTV.m3u8", "https://cn.i.cdn.ti-platform.com/content/480/elmas-kafa/video/tr/elma-kafa.1091ccee.jpg", 1, 3),
                Episode("Ateş Topu Gazabı", "https://ti-content-global.cdn.turner.com/PROD-APAC/C_BEN160_0XXXX0140_REC_TR/752cf85b-1069-46d5-81aa-09ddefdfc9bd/PCTV.m3u8", "https://cn.i.cdn.ti-platform.com/content/480/ates-topu-hex-ve-buharl%C4%B1-smythe/video/tr/ben10tr.0a0b74b0.jpg", 1, 4)
            )
        ),
        CartoonShow(
            title = "Kral Şakir",
            searchKeyword = "Kral Sakir",
            genres = listOf("#yerli", "#komedi", "#aile"),
            description = "Aslan Remzi, Fil Necati, Kürdan Şakir, Canan ve Kadriye'nin Türkiye'ye özgü kültürel ögelerle harmanlanmış komik aile hayatı.",
            rating = "8.1",
            years = "2016-",
            seasonsCount = 5,
            episodes = listOf(
                Episode("Trabzon'a Yolculuk", "https://ti-content-global.cdn.turner.com/PROD-APAC/C_KSAKIR_0XXXX0332_REC_TR/f72742e5-7e84-4b55-b8e2-a7fa0367c2a1/PCTV.m3u8", "https://ti-content-global.cdn.turner.com/PROD-APAC/C_KSAKIR_0XXXX0332_REC_TR/C_KSAKIR_0XXXX0332_REC_TR_VIDSCREENSHOT.jpg", 1, 1),
                Episode("Tanju'nun Evi", "https://ti-content-global.cdn.turner.com/PROD-APAC/C_KSAKIR_0XXXX0334_REC_TR/d0ac42c6-01d8-4a21-becc-fcf36fb6a452/PCTV.m3u8", "https://ti-content-global.cdn.turner.com/PROD-APAC/C_KSAKIR_0XXXX0334_REC_TR/C_KSAKIR_0XXXX0334_REC_TR_VIDSCREENSHOT.jpg", 1, 2),
                Episode("Uzaylı Pudy", "https://ti-content-global.cdn.turner.com/PROD-APAC/C_KSAKIR_0XXXX0335_REC_TR/196f6e3e-26c7-4f1e-ad33-6dd91af2248e/PCTV.m3u8", "https://ti-content-global.cdn.turner.com/PROD-APAC/C_KSAKIR_0XXXX0335_REC_TR/C_KSAKIR_0XXXX0335_REC_TR_VIDSCREENSHOT.jpg", 1, 3),
                Episode("Yalan Dünya", "https://ti-content-global.cdn.turner.com/PROD-APAC/C_KSAKIR_0XXXX0353_REC_TR/0669ac80-efd2-49a8-8350-03fd39d75b82/PCTV.m3u8", "https://ti-content-global.cdn.turner.com/PROD-APAC/C_KSAKIR_0XXXX0353_REC_TR/C_KSAKIR_0XXXX0353_REC_TR_VIDSCREENSHOT.jpg", 1, 4),
                Episode("Sınırsız Para Hilesi", "https://ti-content-global.cdn.turner.com/PROD-APAC/C_KSAKIR_XYXXY208_MET_TUR/f17d56f6-648f-4c2d-9573-f6b547be92d4/PCTV.m3u8", "https://ti-content-global.cdn.turner.com/PROD-APAC/C_KSAKIR_XYXXY208_MET_TUR/C_KSAKIR_XYXXY208_MET_TUR_VIDSCREENSHOT.jpg", 1, 5)
            )
        ),
        CartoonShow(
            title = "Johnny Bravo",
            searchKeyword = "Johnny Bravo",
            genres = listOf("#klasik", "#komedi", "#retro"),
            description = "Yakışıklı olduğunu düşünen kaslı ama şapşal saçlı Johnny Bravo'nun kadınları etkilemeye çalışırken yaşadığı trajikomik hüsranlar.",
            rating = "7.2",
            years = "1997-2004",
            seasonsCount = 4,
            episodes = listOf(
                Episode("Kayıp Anne", "https://ti-content-global.cdn.turner.com/PROD-APAC/C_JOHNNY_01013598_MEI_TUR/d771aef9-7b58-4751-bfae-0ab9e4f84e46/PCTV.m3u8", "https://ti-content-global.cdn.turner.com/PROD-APAC/C_JOHNNY_01013598_MEI_TUR/C_JOHNNY_01013598_MEI_TUR_VIDSCREENSHOT.jpg", 1, 1),
                Episode("Vampir Etkisi", "https://ti-content-global.cdn.turner.com/PROD-APAC/C_JOHNNY_XXXXX191_MEI_TUR/9f59b946-a9a8-4ea0-9571-2e11760ce697/PCTV.m3u8", "https://ti-content-global.cdn.turner.com/PROD-APAC/C_JOHNNY_XXXXX191_MEI_TUR/C_JOHNNY_XXXXX191_MEI_TUR_VIDSCREENSHOT.jpg", 1, 2),
                Episode("Bravo Dooby-Doo", "https://ti-content-global.cdn.turner.com/PROD-APAC/C_JOHNNY_XX002081_MEI_TUR/509be05e-1132-44c1-9743-f57d2007282e/PCTV.m3u8", "https://ti-content-global.cdn.turner.com/PROD-APAC/C_JOHNNY_XX002081_MEI_TUR/C_JOHNNY_XX002081_MEI_TUR_VIDSCREENSHOT.jpg", 1, 3)
            )
        ),
        CartoonShow(
            title = "Samurai Jack",
            searchKeyword = "Samurai Jack",
            genres = listOf("#aksiyon", "#drama", "#savaş"),
            description = "Şeytani büyücü Aku tarafından fütüristik bir geleceğe sürgün edilen asil bir samurayın evine ve geçmişine dönmek için verdiği destansı savaş.",
            rating = "8.5",
            years = "2001-2017",
            seasonsCount = 5,
            episodes = listOf(
                Episode("Jack ve Lav Canavarı", "https://ti-content-global.cdn.turner.com/PROD-APAC/C_SAMURJ_01010659_MEI_TUR/ec2dc376-f380-4985-a26e-e88239d2f469/PCTV.m3u8", "https://ti-content-global.cdn.turner.com/PROD-APAC/C_SAMURJ_01010659_MEI_TUR/C_SAMURJ_01010659_MEI_TUR_VIDSCREENSHOT.jpg", 1, 1),
                Episode("Jack Denizin Altında", "https://ti-content-global.cdn.turner.com/PROD-APAC/C_SAMURJ_XXXXX542_MEI_TUR/bea3267f-1487-49c7-a205-f8e5ca827fad/PCTV.m3u8", "https://ti-content-global.cdn.turner.com/PROD-APAC/C_SAMURJ_XXXXX542_MEI_TUR/C_SAMURJ_XXXXX542_MEI_TUR_VIDSCREENSHOT.jpg", 1, 2),
                Episode("Başlangıç", "https://ti-content-global.cdn.turner.com/PROD-APAC/C_SAMURJ_XX001094_MEI_TUR/37a1218f-4002-4eb3-ae38-aa62a0fc1831/PCTV.m3u8", "https://ti-content-global.cdn.turner.com/PROD-APAC/C_SAMURJ_XX001094_MEI_TUR/C_SAMURJ_XX001094_MEI_TUR_VIDSCREENSHOT.jpg", 1, 3)
            )
        ),
        CartoonShow(
            title = "Kafadar Ayılar (We Bare Bears)",
            searchKeyword = "We Bare Bears",
            genres = listOf("#komedi", "#macera", "#dostluk"),
            description = "Boz, Panda ve Kutup adındaki üç kardaş ayının modern insan toplumuna entegre olmaya çalışırken yaşadığı tatlı, sıcak ve eğlenceli olaylar.",
            rating = "7.9",
            years = "2015-2019",
            seasonsCount = 4,
            episodes = listOf(
                Episode("Ayı Gerçekleri", "https://ti-content-global.cdn.turner.com/PROD-APAC/C_WEBEAR_0XXXX0300_REC_TR/47793020-5e6f-47bb-ae6b-a339e89c7994/PCTV.m3u8", "https://ti-content-global.cdn.turner.com/PROD-APAC/C_WEBEAR_0XXXX0300_REC_TR/C_WEBEAR_0XXXX0300_REC_TR_VIDSCREENSHOT.jpg", 1, 1),
                Episode("Bebek Ayılar", "https://ti-content-global.cdn.turner.com/PROD-APAC/C_WEBEAR_0XXXX0316_REC_TR/b4999e4c-2db0-4ac0-8527-452aa4d3f187/PCTV.m3u8", "https://ti-content-global.cdn.turner.com/PROD-APAC/C_WEBEAR_0XXXX0316_REC_TR/C_WEBEAR_0XXXX0316_REC_TR_VIDSCREENSHOT.jpg", 1, 2),
                Episode("Panda Dansı", "https://ti-content-global.cdn.turner.com/PROD-APAC/C_WEBEAR_0XXXX0303_REC_TR/734a2b7e-55fa-4268-ab48-6b78a1568f11/PCTV.m3u8", "https://ti-content-global.cdn.turner.com/PROD-APAC/C_WEBEAR_0XXXX0303_REC_TR/C_WEBEAR_0XXXX0303_REC_TR_VIDSCREENSHOT.jpg", 1, 3),
                Episode("Aşçı Kutup", "https://ti-content-global.cdn.turner.com/PROD-APAC/C_WEBEAR_0XXXX0312_REC_TR/d00aa6cb-a705-4854-83d7-430761eb49eb/PCTV.m3u8", "https://ti-content-global.cdn.turner.com/PROD-APAC/C_WEBEAR_0XXXX0312_REC_TR/C_WEBEAR_0XXXX0312_REC_TR_VIDSCREENSHOT.jpg", 1, 4)
            )
        ),
        CartoonShow(
            title = "Teen Titans Go!",
            searchKeyword = "Teen Titans Go!",
            genres = listOf("#komedi", "#aksiyon", "#fantastik"),
            description = "Robin, Starfire, Raven, Beast Boy ve Cyborg'un dünyayı kurtarmak yerine pizza yemek ve oyun oynamakla ilgilendikleri aşırı komik maceraları.",
            rating = "8.0",
            years = "2013-",
            seasonsCount = 8,
            episodes = listOf(
                Episode("Titanların Başlangıcı", "https://ti-content-global.cdn.turner.com/PROD-APAC/C_TETIGO_0XXXX0507_REC_TR/f24d98bd-7beb-49bb-8b6a-1e4630b2d395/PCTV.m3u8", "https://ti-content-global.cdn.turner.com/PROD-APAC/C_TETIGO_0XXXX0507_REC_TR/C_TETIGO_0XXXX0507_REC_TR_VIDSCREENSHOT.jpg", 1, 1),
                Episode("Kostüm Yarışması", "https://ti-content-global.cdn.turner.com/PROD-APAC/C_TETIGO_0XXXX0488_REC_TR/558af6a5-f228-419f-9441-0e8409bac3de/PCTV.m3u8", "https://ti-content-global.cdn.turner.com/PROD-APAC/C_TETIGO_0XXXX0488_REC_TR/C_TETIGO_0XXXX0488_REC_TR_VIDSCREENSHOT.jpg", 1, 2),
                Episode("Klasik Titanlar", "https://ti-content-global.cdn.turner.com/PROD-APAC/C_TETIGO_0XXXX0503_REC_TR/55ee2cda-b54e-46e7-a0b4-79efbc7dcd59/PCTV.m3u8", "https://ti-content-global.cdn.turner.com/PROD-APAC/C_TETIGO_0XXXX0503_REC_TR/C_TETIGO_0XXXX0503_REC_TR_VIDSCREENSHOT.jpg", 1, 3),
                Episode("Kedi Robin", "https://ti-content-global.cdn.turner.com/PROD-APAC/C_TETIGO_0XXXX0510_REC_TR/4757c4da-9c9c-4d72-ba8c-e33400bb45ce/PCTV.m3u8", "https://ti-content-global.cdn.turner.com/PROD-APAC/C_TETIGO_0XXXX0510_REC_TR/C_TETIGO_0XXXX0510_REC_TR_VIDSCREENSHOT.jpg", 1, 4)
            )
        ),
        CartoonShow(
            title = "Winx Club",
            searchKeyword = "Winx Club",
            genres = listOf("#sihir", "#dostluk", "#kızlar"),
            description = "Bloom adında dünyalı bir kızın sihirli güçlerini keşfederek Alfea Peri Okulu'na kaydolması ve arkadaşlarıyla Winx Kulübü'nü kurması.",
            rating = "7.0",
            years = "2004-2019",
            seasonsCount = 8,
            episodes = listOf(
                Episode("Melodi ve Dans Yarışması", "https://ti-content-global.cdn.turner.com/PROD-APAC/C_WINXCL_XX203097_MEI_TUR/ea6b3ace-4063-469b-bdd5-0340231dc8e8/PCTV.m3u8", "https://ti-content-global.cdn.turner.com/PROD-APAC/C_WINXCL_XX203097_MEI_TUR/C_WINXCL_XX203097_MEI_TUR_VIDSCREENSHOT.jpg", 1, 1),
                Episode("Yıldızların Gecesi", "https://ti-content-global.cdn.turner.com/PROD-APAC/C_WINXCL_0XXXX0570_REC_TR/8b55b4e9-c312-4a6d-82f6-b03603dfed7c/PCTV.m3u8", "https://ti-content-global.cdn.turner.com/PROD-APAC/C_WINXCL_0XXXX0570_REC_TR/C_WINXCL_0XXXX0570_REC_TR_VIDSCREENSHOT.jpg", 1, 2),
                Episode("Volkan Patlaması", "https://ti-content-global.cdn.turner.com/PROD-APAC/C_WINXCL_0XXXX0618_REC_TR/c0b8abdd-5646-4075-b354-54d60390c2f1/PCTV.m3u8", "https://ti-content-global.cdn.turner.com/PROD-APAC/C_WINXCL_0XXXX0618_REC_TR/C_WINXCL_0XXXX0618_REC_TR_VIDSCREENSHOT.jpg", 1, 3)
            )
        ),
        CartoonShow(
            title = "Clarence",
            searchKeyword = "Clarence",
            genres = listOf("#komedi", "#okul", "#dostluk"),
            description = "Saf kalpli, her duruma ultra-pozitif bakan Clarence ile arkadaşlarının sıradan çocukluk maceraları.",
            rating = "7.5",
            years = "2014-2018",
            seasonsCount = 3,
            episodes = listOf(
                Episode("2 Saatlik Clarence Videosu", "https://ti-content-global.cdn.turner.com/PROD-APAC/C_CLAREN_XXXXX803_MEI_TUR/29c7643e-9585-42c4-a7e2-31220f0f5e33/PCTV.m3u8", "https://ti-content-global.cdn.turner.com/PROD-APAC/C_CLAREN_XXXXX803_MEI_TUR/C_CLAREN_XXXXX803_MEI_TUR_VIDSCREENSHOT.jpg", 1, 1)
            )
        ),
        CartoonShow(
            title = "Masha ve Koca Ayı",
            searchKeyword = "Masha and the Bear",
            genres = listOf("#çocuk", "#komedi", "#doğa"),
            description = "Sürekli başını belaya sokan sevimli mi sevimli küçük kız Maşa ile emekli sirk ayısı Koca Ayı'nın ormanda geçen sıcacık, komik dostluk öyküsü.",
            rating = "8.1",
            years = "2009-",
            seasonsCount = 5,
            episodes = listOf(
                Episode("Bir Yılbaşı Şarkısı", "https://ti-content-global.cdn.turner.com/PROD-APAC/C_MASHAB_XXXXX192_MEI_TUR/341850d9-393f-4765-86e3-3f244f3afa4d/PCTV.m3u8", "https://ti-content-global.cdn.turner.com/PROD-APAC/C_MASHAB_XXXXX192_MEI_TUR/C_MASHAB_XXXXX192_MEI_TUR_VIDSCREENSHOT.jpg", 1, 1),
                Episode("Ben Kimim?", "https://ti-content-global.cdn.turner.com/PROD-APAC/C_CATITO_01007676_MEI_TUR/d25c7457-f192-4e7d-a475-8a0bc88d914b/PCTV.m3u8", "https://ti-content-global.cdn.turner.com/PROD-APAC/C_CATITO_01007676_MEI_TUR/C_CATITO_01007676_MEI_TUR_VIDSCREENSHOT.jpg", 1, 2)
            )
        ),
        CartoonShow(
            title = "Powerpuff Girls",
            searchKeyword = "The Powerpuff Girls",
            genres = listOf("#aksiyon", "#komedi", "#klasik"),
            description = "Kimyasal X karışımıyla süper güçlere sahip olan üç minik kız Blossom, Bubbles ve Buttercup'ın Townsville şehrini canavarlardan korumaları.",
            rating = "7.3",
            years = "2016-2019",
            seasonsCount = 3,
            episodes = listOf(
                Episode("Tehlikeli Uzaylı", "https://ti-content-global.cdn.turner.com/PROD-APAC/C_PPG160_0XXXX0446_REC_TR/848ad2b0-076b-43ac-b996-a9e92e10df9b/PCTV.m3u8", "https://ti-content-global.cdn.turner.com/PROD-APAC/C_PPG160_0XXXX0446_REC_TR/C_PPG160_0XXXX0446_REC_TR_VIDSCREENSHOT.jpg", 1, 1),
                Episode("Bliss Geri Dönüyor", "https://ti-content-global.cdn.turner.com/PROD-APAC/C_PPG160_0XXXX0445_REC_TR/fae28d74-06f3-4a65-9db2-2b6c91efc3e2/PCTV.m3u8", "https://ti-content-global.cdn.turner.com/PROD-APAC/C_PPG160_0XXXX0445_REC_TR/C_PPG160_0XXXX0445_REC_TR_VIDSCREENSHOT.jpg", 1, 2)
            )
        ),
        CartoonShow(
            title = "Elma ve Soğan",
            searchKeyword = "Elma ve Sogan",
            genres = listOf("#komedi", "#macera", "#yemek"),
            description = "Büyük şehre yeni taşınan ve burada birbirleriyle dost olan Elma ve Soğan'ın absürt ve eğlence dolu modern yaşam maceraları.",
            rating = "7.8",
            years = "2018-2021",
            seasonsCount = 2,
            episodes = listOf(
                Episode("Su", "https://ti-content-global.cdn.turner.com/PROD-APAC/C_APPONI_0XXXX0242_REC_TR/ba3f9d15-b0ef-4839-9102-e1e4f8545b5e/PCTV.m3u8", "https://ti-content-global.cdn.turner.com/PROD-APAC/C_APPONI_0XXXX0242_REC_TR/C_APPONI_0XXXX0242_REC_TR_VIDSCREENSHOT.jpg", 1, 1),
                Episode("Uyku", "https://ti-content-global.cdn.turner.com/PROD-APAC/C_APPONI_0XXXX0241_REC_TR/7667d2b8-80b2-4462-800b-79864bbeadaa/PCTV.m3u8", "https://ti-content-global.cdn.turner.com/PROD-APAC/C_APPONI_0XXXX0241_REC_TR/C_APPONI_0XXXX0241_REC_TR_VIDSCREENSHOT.jpg", 1, 2),
                Episode("Seçim Zamanı", "https://ti-content-global.cdn.turner.com/PROD-APAC/C_APPONI_01039914_MEI_TUR/15f2cf9a-4262-43ac-a3b0-621d19dee20e/PCTV.m3u8", "https://ti-content-global.cdn.turner.com/PROD-APAC/C_APPONI_01039914_MEI_TUR/C_APPONI_01039914_MEI_TUR_VIDSCREENSHOT.jpg", 1, 3)
            )
        ),
        CartoonShow(
            title = "Kulak Jack (Flapjack)",
            searchKeyword = "Kulak Jack",
            genres = listOf("#klasik", "#macera", "#komedi"),
            description = "Kibar bir çocuk olan Flapjack ve huysuz korsan Kaptan Yosun'un konuşan balina Bubbie'nin ağzında yaşayıp Şeker Adası'nı arama maceraları.",
            rating = "7.7",
            years = "2008-2010",
            seasonsCount = 3,
            episodes = listOf(
                Episode("Sardalya Adası Canavarı", "https://ti-content-global.cdn.turner.com/PROD-APAC/C_FLAPJA_0XXXX0370_REC_TR/5dbb7647-5edd-469c-8f59-18b7f9c924e0/PCTV.m3u8", "https://ti-content-global.cdn.turner.com/PROD-APAC/C_FLAPJA_0XXXX0370_REC_TR/C_FLAPJA_0XXXX0370_REC_TR_VIDSCREENSHOT.jpg", 1, 1),
                Episode("Şeker Kağıdı", "https://ti-content-global.cdn.turner.com/PROD-APAC/C_FLAPJA_0XXXX0371_REC_TR/96337535-23c5-459b-a14b-026b35c6f6aa/PCTV.m3u8", "https://ti-content-global.cdn.turner.com/PROD-APAC/C_FLAPJA_0XXXX0371_REC_TR/C_FLAPJA_0XXXX0371_REC_TR_VIDSCREENSHOT.jpg", 1, 2),
                Episode("Uçan Gemi", "https://ti-content-global.cdn.turner.com/PROD-APAC/C_FLAPJA_0XXXX0372_REC_TR/fee98a0a-a2b0-4d21-bef2-2a3af2f12705/PCTV.m3u8", "https://ti-content-global.cdn.turner.com/PROD-APAC/C_FLAPJA_0XXXX0372_REC_TR/C_FLAPJA_0XXXX0372_REC_TR_VIDSCREENSHOT.jpg", 1, 3)
            )
        ),
        CartoonShow(
            title = "Uncle Grandpa",
            searchKeyword = "Uncle Grandpa",
            genres = listOf("#komedi", "#absürt", "#aile"),
            description = "Dünyadaki herkesin büyük amcası ve büyükbabası olan Uncle Grandpa'nın ve arkadaşlarının sihirli fütüristik karavan maceraları.",
            rating = "5.4",
            years = "2013-2017",
            seasonsCount = 5,
            episodes = listOf(
                Episode("Bilgisayar", "https://ti-content-global.cdn.turner.com/PROD-APAC/C_UNCGPA_0XXXX0546_REC_TR/06114206-fdfa-46ec-a9d9-182fb144a4a6/PCTV.m3u8", "https://ti-content-global.cdn.turner.com/PROD-APAC/C_UNCGPA_0XXXX0546_REC_TR/C_UNCGPA_0XXXX0546_REC_TR_VIDSCREENSHOT.jpg", 1, 1),
                Episode("Çizgifilm Fabrikası", "https://ti-content-global.cdn.turner.com/PROD-APAC/C_UNCGPA_0XXXX0545_REC_TR/d54d3880-ec25-4432-a30f-3d0c7a2bd2a9/PCTV.m3u8", "https://ti-content-global.cdn.turner.com/PROD-APAC/C_UNCGPA_0XXXX0545_REC_TR/C_UNCGPA_0XXXX0545_REC_TR_VIDSCREENSHOT.jpg", 1, 2)
            )
        ),
        CartoonShow(
            title = "Dexter'ın Laboratuvarı",
            searchKeyword = "Dexterin Laboratuari",
            genres = listOf("#klasik", "#bilim", "#komedi"),
            description = "Dahi çocuk Dexter'ın odasındaki gizli devasa laboratuvarda yaptığı deneyler ve onu sürekli rahatsız eden ablası Dee Dee ile mücadelesi.",
            rating = "7.9",
            years = "1996-2003",
            seasonsCount = 4,
            episodes = listOf(
                Episode("Dexter'ın Rakibi", "https://ti-content-global.cdn.turner.com/PROD-APAC/C_DEXTER_01003827_MEI_TUR/303e1c36-d949-4ed0-834c-d80e1e336cc6/PCTV.m3u8", "https://ti-content-global.cdn.turner.com/PROD-APAC/C_DEXTER_01003827_MEI_TUR/C_DEXTER_01003827_MEI_TUR_VIDSCREENSHOT.jpg", 1, 1),
                Episode("Zaman Yolculuğu", "https://ti-content-global.cdn.turner.com/PROD-APAC/C_DEXTER_0XXXX0223_REC_TR/1a2cdb6a-5d96-4641-b522-2c01f57dc813/PCTV.m3u8", "https://ti-content-global.cdn.turner.com/PROD-APAC/C_DEXTER_0XXXX0223_REC_TR/C_DEXTER_0XXXX0223_REC_TR_VIDSCREENSHOT.jpg", 1, 2)
            )
        ),
        CartoonShow(
            title = "Foster'ın Hayali Dostlar Mekanı",
            searchKeyword = "Foster",
            genres = listOf("#klasik", "#hayali", "#aile"),
            description = "Sekiz yaşındaki Mac'in hayali arkadaşı Bloo ile birlikte terkedilmiş ya da evlat edinilmeyi bekleyen tüm hayali canlıların kaldığı Foster's evindeki maceraları.",
            rating = "7.6",
            years = "2004-2009",
            seasonsCount = 6,
            episodes = listOf(
                Episode("Karalama Belası", "https://ti-content-global.cdn.turner.com/PROD-APAC/C_FOSTER_01015807_MEI_TUR/ae282861-4c44-4548-b6b1-2a4f43bbbe8a/PCTV.m3u8", "https://ti-content-global.cdn.turner.com/PROD-APAC/C_FOSTER_01015807_MEI_TUR/C_FOSTER_01015807_MEI_TUR_VIDSCREENSHOT.jpg", 1, 1),
                Episode("Blooooo'nun Korku Evi", "https://ti-content-global.cdn.turner.com/PROD-APAC/C_FOSTER_01013678_MEI_TUR/ab89961d-2916-4f34-8404-16c6b374ffdc/PCTV.m3u8", "https://ti-content-global.cdn.turner.com/PROD-APAC/C_FOSTER_01013678_MEI_TUR/C_FOSTER_01013678_MEI_TUR_VIDSCREENSHOT.jpg", 1, 2)
            )
        )
    )
}
