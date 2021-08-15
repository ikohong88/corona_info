$(function() {
    $.ajax({
        type:"get",
        url:"/api/coronaRegionInfo?date=today",
        success:function(r) {
            console.log(r);
            // let _incDec = new Array();
            // for(let i=1; i<r.data.length; i++) {
            //     let cnt = r.data[i].incDec;
            //     _incDec.push(cnt);
            // } 
            let today = new Date();
            let year = today.getFullYear();        
            let month = today.getMonth()+1;        
            let date = today.getDate();        
            let day = today.getDay();
            let _today = null;
            if(day == 0) _today ="일";        
            else if(day == 1) _today ="월";        
            else if(day == 2) _today ="화";        
            else if(day == 3) _today ="수";        
            else if(day == 4) _today ="목";        
            else if(day == 5) _today ="금";        
            else if(day == 6) _today ="토";        
              
            let hour = today.getHours();        
            let minutes = today.getMinutes();        
            let second = today.getSeconds();
            let _time = year + "-" + month + "-" + date +"("+_today+") "+hour+":"+minutes+":"+second; 
            let incDec = new Array();
            for(i=1;i<r.data.length;i++) {
                let _incDec = r.data[i].incDec;
                incDec.push(_incDec);
            }      
            let ctx = $("#regional_status");
            let regionalChart = new Chart (ctx, {
                type:"bar",
                options:{
                    responsive:false                                  
                },
                data:{
                    labels:['충북','충남','제주','전북','전남','인천','울산','세종','서울','부산'
                    ,'대전','대구','광주','경북','경남','경기','검역','강원'],
                    datasets:[{
                        label:_time+" 신규확진 합계 : "+r.data[0].incDec,
                        data:incDec                                                 
                            // r.data[1].incDec,
                            // r.data[2].incDec,
                            // r.data[3].incDec,
                            // r.data[4].incDec,
                            // r.data[5].incDec,
                            // r.data[6].incDec,
                            // r.data[7].incDec,
                            // r.data[8].incDec,
                            // r.data[9].incDec,
                            // r.data[10].incDec,
                            // r.data[11].incDec,
                            // r.data[12].incDec,
                            // r.data[13].incDec,
                            // r.data[14].incDec,
                            // r.data[15].incDec,
                            // r.data[16].incDec,
                            // r.data[17].incDec,
                            // r.data[18].incDec
                        ,
                        backgroundColor:['rgba(255,30,30,0.7)']                
                    }]
                }
            })
            var tag = "";

            let sidoName = new Array();
            let defCnt = new Array();

            for(let i=0; i<7; i++) {
                let _tag = "<tbody class='region-tbody'></tbody>";
                $(".region_confirm_tbl").append(_tag);
            }
            for(let i=0; i<r.data.length; i++) {
                let sido = r.data[i].gubun;
                let cnt = r.data[i].incDec;
                sidoName.push(sido);
                defCnt.push(cnt);
                let page = Math.floor(i/3);
                tag = 
                '<tr>'+
                    '<td>'+r.data[i].gubun+'</td>'+
                    '<td>'+r.data[i].defCnt+'</td>'+
                    '<td>'+r.data[i].incDec+' ▲</td>'+
                '</tr>'
                $(".region-tbody").eq(page).append(tag); 
            }
            $(".region-tbody").eq(0).addClass("active");

            $("#region_next").click(function() {
                let currentPage = Number($(".current").html());
                currentPage++;
                if(currentPage > 7) currentPage = 7;
                $(".current").html(currentPage);
                $(".region-tbody").removeClass("active");
                $(".region-tbody").eq(currentPage-1).addClass("active");
            })
            $("#region_prev").click(function() {
                let currentPage = Number($(".current").html());
                currentPage--;
                if(currentPage <= 0) currentPage = 1;
                $(".current").html(currentPage);
                $(".region-tbody").removeClass("active");
                $(".region-tbody").eq(currentPage-1).addClass("active");
            })
            // for(var i = 0; i<19; i++) {
            //     tag += '<tr><td class="region">'+
            //     r.data[i].gubun+'</td><td class="totalCnt">'+
            //     r.data[i].defCnt+'</td><td class="increaseCnt">'+
            //     r.data[i].incDec+' ▲</td></tr>';                          
            // }
            // $("#region_confirm_tbd").append(tag);           
        }
    })

    $.ajax({
        type:"get",
        url:"/api/coronaInfo",
        success:function(r) {
            console.log(r);
            $("#accExamCnt").html(r.data[0].strAccExamCnt);
            $("#decideCnt").html(r.data[0].strDecideCnt);
            let ctx2 = $("#confirmed_chart");
            let confirmed_chart = new Chart (ctx2, {
                type:"pie",
                options:{
                    responsive:false
                },
                data:{
                    labels:["확진","음성"],
                    datasets:[{
                        label:"확진/음성",
                        data:[
                            r.data[0].decideCnt-r.data[1].decideCnt,
                            r.data[0].resultNegCnt-r.data[1].resultNegCnt],
                        backgroundColor:['rgba(255,30,30,0.7)','rgba(0,0,255,0.4)']                
                    }]
                }
            })
        }
    })

    $.ajax({
        type:"get",
        url:"/api/vaccineinfo",
        success:function(r) {
            console.log(r);            
            let firstCnt = new Array();
            let SecondCnt = new Array();
            for (let i=0;i<r.data.length;i++){
                if(i===5) continue;
                let _firstCnt = r.data[i].firstCnt;
                let _SecondCnt = r.data[i].secondCnt;
                firstCnt.push(_firstCnt);
                SecondCnt.push(_SecondCnt);
            };                                          
            let ctx3 = $("#vaccine_chart");
            window.vaccine_chart = new Chart (ctx3, {
                type:"bar",
                options:{
                    responsive:false
                },
                data:{
                    labels:['충북','충남','제주','전북','전남','인천','울산','세종','서울','부산'
                    ,'대전','대구','광주','경북','경남','경기','강원'],
                    datasets:[{
                        label:r.data[0].baseDate+" 1차 접종 현황 : "+r.data[5].firstCnt,
                        data:firstCnt                                              
                            // r.data[0].firstCnt,                                                   
                            // r.data[1].firstCnt,
                            // r.data[2].firstCnt,
                            // r.data[3].firstCnt,
                            // r.data[4].firstCnt,                            
                            // r.data[6].firstCnt,
                            // r.data[7].firstCnt,
                            // r.data[8].firstCnt,
                            // r.data[9].firstCnt,
                            // r.data[10].firstCnt,
                            // r.data[11].firstCnt,
                            // r.data[12].firstCnt,
                            // r.data[13].firstCnt,
                            // r.data[14].firstCnt,
                            // r.data[15].firstCnt,
                            // r.data[16].firstCnt,
                            // r.data[17].firstCnt
                        ,
                        backgroundColor:['rgba(30,255,30,0.7)']                
                    },{
                        label:"2차 접종 현황 : "+r.data[5].secondCnt,
                        data:SecondCnt
                            // r.data[0].secondCnt,
                            // r.data[1].secondCnt,
                            // r.data[1].secondCnt,
                            // r.data[2].secondCnt,
                            // r.data[3].secondCnt,
                            // r.data[4].secondCnt,                            
                            // r.data[6].secondCnt,
                            // r.data[7].secondCnt,
                            // r.data[8].secondCnt,
                            // r.data[9].secondCnt,
                            // r.data[10].secondCnt,
                            // r.data[11].secondCnt,
                            // r.data[12].secondCnt,
                            // r.data[13].secondCnt,
                            // r.data[14].secondCnt,
                            // r.data[15].secondCnt,
                            // r.data[16].secondCnt,
                            // r.data[17].secondCnt                                                   
                        ,
                        backgroundColor:['rgba(30,30,255,0.7)']  
                    }]
                }
            })
            // window.vaccine_chart.destroy();  
        }
    })
    
    $.ajax({
        type:"get",
        url:"/api/selectGen",
        success:function(r) {
            console.log(r);
            let ctx4 = $("#gen_chart");
            let confirmed_chart = new Chart (ctx4, {
                type:"pie",
                options:{
                    responsive:false
                },
                data:{
                    labels:["여성","남성"],
                    datasets:[{
                        label:"여성/남성",
                        data:[
                            r.data[0].confCase-r.data[2].confCase,
                            r.data[1].confCase-r.data[3].confCase],
                        backgroundColor:['rgba(255,30,30,0.7)','rgba(0,0,255,0.4)']                
                    }]
                }
            })
        }
    })

    $.ajax({
        type:"get",
        url:"/api/selectAge",
        success:function(r) {
            console.log(r);
            let totalDeath = 
            (r.data[0].death-r.data[9].death)+
            (r.data[1].death-r.data[10].death)+
            (r.data[2].death-r.data[11].death)+
            (r.data[3].death-r.data[12].death)+
            (r.data[4].death-r.data[13].death)+
            (r.data[5].death-r.data[14].death)+
            (r.data[6].death-r.data[15].death)+
            (r.data[7].death-r.data[16].death)+
            (r.data[8].death-r.data[17].death);
            let totalconfCase = 
            (r.data[0].confCase-r.data[9].confCase)+
            (r.data[1].confCase-r.data[10].confCase)+
            (r.data[2].confCase-r.data[11].confCase)+
            (r.data[3].confCase-r.data[12].confCase)+
            (r.data[4].confCase-r.data[13].confCase)+
            (r.data[5].confCase-r.data[14].confCase)+
            (r.data[6].confCase-r.data[15].confCase)+
            (r.data[7].confCase-r.data[16].confCase)+
            (r.data[8].confCase-r.data[17].confCase);    
            let ctx3 = $("#age_chart");
            let vaccine_chart = new Chart (ctx3, {
                type:"line",
                options:{
                    responsive:false
                },
                data:{
                    labels:['0-9','10-19','20-29','30-39','40-49','50-59','60-69','70-79','80 이상'],
                    datasets:[{
                        label:r.data[0].createDt+" | 확진수 : "+totalconfCase,
                        data:[                            
                            (r.data[0].confCase-r.data[9].confCase),
                            (r.data[1].confCase-r.data[10].confCase),
                            (r.data[2].confCase-r.data[11].confCase),
                            (r.data[3].confCase-r.data[12].confCase),
                            (r.data[4].confCase-r.data[13].confCase),
                            (r.data[5].confCase-r.data[14].confCase),
                            (r.data[6].confCase-r.data[15].confCase),
                            (r.data[7].confCase-r.data[16].confCase),
                            (r.data[8].confCase-r.data[17].confCase)                           
                        ],
                        backgroundColor:['rgba(30,255,30,0.7)']                
                    },{
                        label:r.data[0].createDt+" | 사망수 : "+totalDeath,
                        data:[
                            (r.data[0].death-r.data[9].death),
                            (r.data[1].death-r.data[10].death),
                            (r.data[2].death-r.data[11].death),
                            (r.data[3].death-r.data[12].death),
                            (r.data[4].death-r.data[13].death),
                            (r.data[5].death-r.data[14].death),
                            (r.data[6].death-r.data[15].death),
                            (r.data[7].death-r.data[16].death),
                            (r.data[8].death-r.data[17].death)  
                        ],
                        backgroundColor:['rgba(30,30,255,0.7)']  
                    }]
                }
            })
        }
    })
    // $.ajax({
    //     type:"get",
    //     // contentType:"text/xml",
    //     // charset="UTF-8",
    //     url:"https://nip.kdca.go.kr/irgd/cov19stats.do",
    //     data:"list=all",
    //     dataType:"xml",
    //     success:function(xml) {
    //         console.log(xml);
    //     }
    // })

    // let ctx = $("#regional_status");
    // let regionalChart = new Chart (ctx, {
    //     type:"bar",
    //     options:{
    //         responsive:false
    //     },
    //     data:{
    //         labels:['서울','대구','인천','부산','경남','경북','충남','강원','대전','충북'
    //         ,'광주','울산','전북','전남','제주','세종'],
    //         datasets:[{
    //             label:"2021-08-09 신규확진",
    //             data:[415,408,86,45,123,88,30,68,13,24,42,39,19,25,21,14,11,1],
    //             backgroundColor:['rgba(255,30,30,0.7)']                
    //         }]
    //     }
    // })
    // let ctx2 = $("#confirmed_chart");
    // let confirmed_chart = new Chart (ctx2, {
    //     type:"pie",
    //     options:{
    //         responsive:false
    //     },
    //     data:{
    //         labels:["확진","음성"],
    //         datasets:[{
    //             label:"확진/음성",
    //             data:[100,200],
    //             backgroundColor:['rgba(255,30,30,0.7)']                
    //         }]
    //     }
    // })
    // s

})