$(function() {
    $("#region_select").change(function() {
        let region = $(this).find("option:selected").val();
        console.log(region);
        getCoronaSidoInfo(region);
        getCoronaVaccineSidoInfo(region);
    });
    // getCoronaSidoInfo("서울");
        
    function getCoronaSidoInfo(sido) {
        $.ajax({
            type:"get",
            url:"/api/regional?region="+sido,
            success:function(r)
             {
                console.log(r);
                $("#accDecodeCnt").html(r.data.defCnt);
                $("#newDecodeCnt").html(r.data.incDec);
                $("#isolateCnt").html(r.data.isolingCnt);
                $("#clearIsolateCnt").html(r.data.isolClearCnt);
                $("#covidDanger span").css("display","none");
                let danger = r.data.incDec - r.data.diff;
                console.log(danger)
                if(danger >= 200) {
                    $("#covidDanger span").eq(3).css("display","inline").css("color","red");
                }
                else if (danger >= 100) {
                    $("#covidDanger span").eq(2).css("display","inline").css("color","light red");
                }
                else if (danger >= 10) {
                    $("#covidDanger span").eq(1).css("display","inline").css("color","blue");
                } else {
                    $("#covidDanger span").eq(0).css("display","inline").css("color","green");
                }            
            }
        })
    }
    function getCoronaVaccineSidoInfo(sido) {
        $.ajax({
            type:"get",
            url:"/api/vaccineinfo",
            success:function(r) {
                console.log(r); 
                // $("#vaccienFirstCnt").html(r.data.firstCnt);
                // $("#vaccienSecondCnt").html(r.data.secondCnt);
            }
        })
    }
})