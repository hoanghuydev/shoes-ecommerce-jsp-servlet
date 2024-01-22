<%--
  Created by IntelliJ IDEA.
  User: HUY
  Date: 1/21/2024
  Time: 9:02 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/common/taglib.jsp" %>
<html>
<head>
    <title>Admin Nai - Edit Image</title>
</head>
<body>
<%--Choose img and preview--%>
    <div class="d-flex justify-content-between p-4" style="background: #f3f3f3; border-radius: 10px;margin-bottom: 15px;max-height: 45vh;overflow-y: auto ">
        <input type="file" accept="image/*" multiple id="listImageEdit"/>
       <div style="min-width: 300px; max-width: 400px;display: flex" >
           <div id="preview" style="margin: auto">
               <img src="" id="previewImg" width="100%" height="100%"/>
           </div>
       </div>
    </div>
<%--Action--%>
    <div style="background: #f3f3f3; padding: 20px; border-radius: 10px;max-height: 40vh;overflow-y: auto;position: relative">
        <div id="edit" style="position: sticky; z-index : 100;padding : 20px;top : -20px; right: 0px; left: 0px;background: #f3f3f3;box-shadow:0px 2px 0px 0px rgba(0, 0, 0, .2);">
            <div id="actionConfigEdit" style="display: flex">
                <button class="mb-3 me-4" id="addText" disabled="">
                    Add text<i class="ms-2 fa-solid fa-paragraph"></i>
                </button>
                <button id="addOverlay" disabled>
                    Add Overlay Image<i class=" ms-2 fa-solid fa-images"></i>
                </button> <button class="btn btn-outline-primary mx-auto" id="exportImg" disabled>EXPORT</button>
            </div>
        </div>
<%--        List config edit--%>
        <div id="listConfigEdit" style="margin-top: 60px">

        </div>
    </div>
<script src="/packages/node_modules/file-saver/dist/FileSaver.min.js"></script>
<script !src="">
    window.addEventListener("DOMContentLoaded",async function () {
        function uuidv4() {
            return "10000000-1000-4000-8000-100000000000".replace(/[018]/g, c =>
                (c ^ crypto.getRandomValues(new Uint8Array(1))[0] & 15 >> c / 4).toString(16)
            );
        }
        // Set up ffmpeg
        const { fetchFile } = FFmpegUtil;
        const { FFmpeg } = FFmpegWASM;
        let ffmpeg = null;
        setUpFfmpeg();
        // init element, format value
        const TYPE_OVERLAY = "overlay";
        const TYPE_TEXT = "text";
        const previewDiv = $("#preview");
        let imageInfo = {
            elementWidth : 0,
            elementHeight : 0,
            pixelWidth : 0,
            pixelHeight : 0,
        }
        let listImage = [];
        let listConfigEdit = [];
        const formatConfigEdit = (id,type,infoConfig) => {
            return {
                id,
                type,
                infoConfig
            }
        }
        const formatInfoConfig = (content,width,height,x,y) => {
            return {
                content,
                width,
                height,
                x,
                y
            }
        }
    // Render DOM list image edit
        $("#listImageEdit").change(function () {
            listImage = $(this)[0].files;
            urlFirstImage = listImage[0] ? URL.createObjectURL(listImage[0]) : "";
            // Get real w,h and w,h of element img to calc ratio
            const imgFirst = new Image();
            imgFirst.onload = function() {
                imageInfo.pixelWidth = this.width;
                imageInfo.pixelHeight = this.height;
            }
            imgFirst.src = urlFirstImage;
            const previewImg = $("#previewImg");
            previewImg.on("load", function () {
                console.log(previewImg.width());
                imageInfo.elementWidth = previewImg.width();
                imageInfo.elementHeight = previewImg.height();
            });
            previewImg.attr("src", urlFirstImage);

            $("#addText").prop("disabled",false);
            $("#addOverlay").prop("disabled",false);
            $("#exportImg").prop("disabled",false);
        })
        $("#addText").click(function () {
            const id = uuidv4();
            const textConfigEditDefault = formatConfigEdit(id,TYPE_TEXT,formatInfoConfig("Text"+id,16,0,0,0));
            $("#listConfigEdit").append(DOMTextConfigEdit(id,textConfigEditDefault.infoConfig));
            listConfigEdit.push(textConfigEditDefault);
            renderDOMEitPreview(textConfigEditDefault);
            reloadEventElement();
        })
        $("#addOverlay").click(function () {
            const id = uuidv4();
            const overlayConfigEditDefault = formatConfigEdit(id,TYPE_OVERLAY,formatInfoConfig("",50,50,0,0));
            $("#listConfigEdit").append(DOMOverlayConfigEdit(id,overlayConfigEditDefault.infoConfig));
            listConfigEdit.push(overlayConfigEditDefault);
            renderDOMEitPreview(overlayConfigEditDefault)
            reloadEventElement();
        })
      // Reload event of element
      function reloadEventElement() {
            // Update DOM preview when change input
          let timeUpdateConfigInfo;
            $(".input-group > input").change(function () {
                const inputDiv = $(this);
                clearTimeout(timeUpdateConfigInfo);
                timeUpdateConfigInfo = setTimeout(function () {
                    const name = inputDiv.attr("name");
                    const id = inputDiv.attr("data-id");
                    const tmpConfigEdit = listConfigEdit.find((config)=> config.id === id);
                    if (tmpConfigEdit) {
                        if (name==="content" && tmpConfigEdit.type === TYPE_OVERLAY) {
                            tmpConfigEdit.infoConfig[name] = inputDiv[0].files[0];
                        } else {
                            tmpConfigEdit.infoConfig[name] = inputDiv.val();
                        }
                        renderDOMEitPreview(tmpConfigEdit);
                        reloadEventElement();
                    }
                },250);
            });
            // Delete config edit and dom preview edit
            $(".removeEdit").click(function () {
                const id = $(this).attr("data-id");
                $("#"+id).remove();
                $("#previewEdit"+id).remove();
                listConfigEdit = listConfigEdit.filter((config)=> config.id!=id);
            })
          // update event drag element in preview
          for (let configEdit of listConfigEdit) {
              const parent = previewDiv;
              const child = $('#previewEdit'+configEdit.id);
              eventMoveElement(parent,child);
          }
      }
        // End reload
        // Handle export image
        $("#exportImg").click(async function () {
            setUpFfmpeg();
            const fontName = 'arial.ttf';
            await ffmpeg.writeFile(fontName, await fetchFile('https://raw.githubusercontent.com/ffmpegwasm/testdata/master/arial.ttf'));
            let execCommandArr = ['-i',"tempInput.png"];
            const ratioPixel = (imageInfo.pixelWidth>imageInfo.elementWidth) ? imageInfo.pixelWidth/imageInfo.elementWidth : imageInfo.elementWidth/imageInfo.pixelWidth;
            const listConfigEditText = listConfigEdit.filter((config)=> config.type===TYPE_TEXT);
            const listConfigEditOverlay = listConfigEdit.filter((config)=> config.type===TYPE_OVERLAY);
            for (let i = 1;i<=listConfigEditOverlay.length;i++) {
                const configEdit = listConfigEditOverlay[i-1];
                const overlayFile = configEdit.infoConfig.content;
                const nameOverlay = overlayFile.name;
                const outNameOverlay = 'out'+nameOverlay;
                const w = Math.round(configEdit.infoConfig.width *ratioPixel);
                const h = Math.round(configEdit.infoConfig.height * ratioPixel);
                console.log(configEdit.infoConfig.content);
                await ffmpeg.writeFile(nameOverlay, await fetchFile(overlayFile));
                await ffmpeg.exec(['-i', nameOverlay, '-vf','scale='+w+':'+h, outNameOverlay]);
                execCommandArr.push('-i');
                execCommandArr.push(outNameOverlay);
            }
            execCommandArr.push('-filter_complex');
            let filter_complex = "";
            for (let i = 1;i<=listConfigEditOverlay.length;i++) {
                const infoConfigOverlay = listConfigEditOverlay[i-1].infoConfig;
                const breakCommand = i===1 ? "[0]" : "[base"+(i-1)+"],[base"+(i-1)+"]";
                const x = Math.round(infoConfigOverlay.x * ratioPixel);
                const y = Math.round(infoConfigOverlay.y * ratioPixel);
                filter_complex += breakCommand+"["+i+"]overlay="+x +":" +y
            }
            for (let i = 1;i<=listConfigEditText.length;i++) {

                const infoConfigText = listConfigEditText[i-1].infoConfig;
                const breakCommand = i===1 ? (listConfigEditOverlay.length>0) ? "," : "": "";
                const x = Math.round(infoConfigText.x * ratioPixel);
                const y = Math.round(infoConfigText.y * ratioPixel);
                const fontSize = Math.round(infoConfigText.width * ratioPixel);
                filter_complex +=breakCommand+ "drawtext=fontfile=" +fontName +":text='" +infoConfigText.content +"':x=" +x +":y=" +(y+fontSize) +":fontsize=" +fontSize;
            }
            execCommandArr.push(filter_complex);
            execCommandArr.push("tempOutput.png");
            for (let imageFile of listImage) {
                const typeInputImage = imageFile.type;
                const inputImageName = imageFile.name;
                const extInputImage = inputImageName.split(".")[inputImageName.split(".").length-1];
                const outputImageName = inputImageName.replace("."+extInputImage,"_edited."+extInputImage);
                console.log(extInputImage);
                console.log(outputImageName);
                await ffmpeg.writeFile(inputImageName, await fetchFile(imageFile));
                try {
                    execCommandArr[1] = inputImageName;
                    execCommandArr[execCommandArr.length-1] = outputImageName;
                    console.log(execCommandArr);
                    await ffmpeg.exec(execCommandArr);
                    const data = await ffmpeg.readFile(outputImageName);
                    const imageUrl = URL.createObjectURL(
                        new Blob([data.buffer], { type: typeInputImage })
                    );
                    const blob = await fetch(imageUrl).then((response) => response.blob());
                    await saveAs(blob, outputImageName);
                    console.log('FFmpeg command executed successfully.');
                } catch (error) {
                    console.error('Error executing FFmpeg command:', error);
                }
            }



        })
        // Render and String DOM Html
        function renderDOMEitPreview (formatConfigEdit) {
            $("#previewEdit"+formatConfigEdit.id).remove();
            let previewEdit = "";
            if (formatConfigEdit.type===TYPE_OVERLAY) {
                const urlImg = formatConfigEdit.infoConfig.content ? URL.createObjectURL(formatConfigEdit.infoConfig.content) : ""
                previewEdit = `
                    <img src="`+urlImg+`" style="position: absolute;top: `+formatConfigEdit.infoConfig.y+`px; left: `+formatConfigEdit.infoConfig.x+`px;width: `+formatConfigEdit.infoConfig.width+`px;height: `+formatConfigEdit.infoConfig.height+`px;" id="previewEdit`+formatConfigEdit.id+`" data-id="`+formatConfigEdit.id+`"/>
                `
            } else if (formatConfigEdit.type === TYPE_TEXT) {
                previewEdit = `
                    <p  style="position: absolute;top: `+formatConfigEdit.infoConfig.y+`px; left: `+formatConfigEdit.infoConfig.x+`px;font-size: `+formatConfigEdit.infoConfig.width+`px;" id="previewEdit`+formatConfigEdit.id+`" data-id="`+formatConfigEdit.id+`">`+formatConfigEdit.infoConfig.content+`</p>
                `
            }
            previewDiv.append(previewEdit);

        }

        const DOMOverlayConfigEdit = (id,infoConfig) => {
            return `
    <div class="my-2" id="`+id+`">
        <div style="display: flex">
            <p class="me-4">Overlay</p>
            <p class="removeEdit" data-id="`+id+`" style="color :red; font-size: 16px"><i class="fa-solid fa-trash"></i></p>
        </div>
        <div class="row">
            <div class="col-12 mb-3">
                <div class="input-group  input-group-outline">
                    <input type="file" accept="image/*" class="form-control" data-id="`+id+`" name="content" id="overlayImg`+id+`">
                </div>
            </div>
            <div class="col-6">
                <div class="input-group  input-group-outline">
                    <label class="">Overlay width</label>
                    <input type="number" class="form-control" value="`+infoConfig.width+`" name="width" data-id="`+id+`" id="overlayW`+id+`">
                </div>
            </div>
            <div class="col-6 ">
                <div class="input-group  input-group-outline">
                    <label class="">Overlay height</label>
                    <input type="number" class="form-control" name="height" value="`+infoConfig.height+`" data-id="`+id+`" id="overlayH`+id+`">
                </div>
            </div>
            <div class=" col-6">
                <div class="input-group  input-group-outline">
                    <label class="">Overlay position x</label>
                    <input type="number" class="form-control" name="x" value="`+infoConfig.x+`" data-id="`+id+`" id="overlayX`+id+`">
                </div>
            </div>
            <div class=" col-6">
                <div class="input-group  input-group-outline">
                    <label class="">Overlay position y</label>
                    <input type="number" class="form-control" name="y" value="`+infoConfig.y+`" data-id="`+id+`" id="overlayY`+id+`">
                </div>
            </div>
        </div>
    </div>
            `
        }
        const DOMTextConfigEdit = (id,infoConfig)=> {
            return `
                <div class="my-2" id="`+id+`">
                <div style="display: flex">
                    <p class="me-4">Text</p>
                    <p class="removeEdit" data-id="`+id+`" style="color :red; font-size: 16px"><i class="fa-solid fa-trash"></i></p>
                </div>
                <div class="row">
                    <div class=" col-6">
                        <div class="input-group  input-group-outline">
                            <label class="">Text content</label>
                            <input type="text" class="form-control" name="content" value="`+infoConfig.content+`" data-id="`+id+`" id="textContent`+id+`">
                        </div>
                    </div>
                    <div class=" col-6">
                        <div class="input-group  input-group-outline">
                            <label class="">Text size</label>
                            <input type="number" class="form-control" name="width" value="`+infoConfig.width+`" data-id="`+id+`" id="textSize`+id+`">
                        </div>
                    </div>
                    <div class=" col-6">
                        <div class="input-group  input-group-outline">
                            <label class="">Text position x</label>
                            <input type="number" class="form-control" name="x" value="`+infoConfig.x+`" data-id="`+id+`" id="textX`+id+`">
                        </div>
                    </div>
                    <div class=" col-6">
                        <div class="input-group  input-group-outline">
                            <label class="">Text position y</label>
                            <input type="number" class="form-control" name="y" value="`+infoConfig.y+`" data-id="`+id+`" id="textY`+id+`">
                        </div>
                    </div>
                </div>
            </div>
            `
        }
        // End Render And DOM
        async function setUpFfmpeg() {
            if (ffmpeg === null) {
                ffmpeg = new FFmpeg();
                ffmpeg.on("log", ({ message }) => {
                    console.log(message);
                })
                ffmpeg.on("progress", ({ progress, time }) => {
                    console.log("Process : "+progress*100);
                });
                await ffmpeg.load({
                    coreURL: "/packages/node_modules/ffmpeg-old/assets/core/package/dist/umd/ffmpeg-core.js",
                });
            }
        }

        // Drag when click element
        function eventMoveElement(parent, child) {
            let isMoving = false;
            let timeoutUpdatePosition;
            const id = child.attr("data-id");
            child.mousedown(function(e) {
                $("#listConfigEdit > div").removeClass("active");
                $("#"+id).addClass("active");
                const divParent = $("#edit");
                scrollToElm(divParent[0],child[0],200);
                isMoving = true;
                let parentRect = parent[0].getBoundingClientRect();
                let sx = e.clientX - parentRect.left - e.offsetX;
                let sy = e.clientY - parentRect.top - e.offsetY;
                let ofX = e.offsetX;
                let ofY = e.offsetY;
                child.css({ 'left': sx + 'px', 'top': sy + 'px', 'pointer-events': 'none' });
                // Update info config edit
                parent.mousemove(function(e) {
                    let parentRect1 = parent[0].getBoundingClientRect();
                    let childRect1 = child[0].getBoundingClientRect();
                    if (isMoving) {
                        let cx = e.offsetX - ofX;
                        let cy = e.offsetY - ofY;
                        if (cx < 1) cx = 0;
                        if (cy < 1) cy = 0;
                        if (cx + childRect1.width > parentRect1.width) cx = parentRect1.width - childRect1.width;
                        if (cy + childRect1.height > parentRect1.height) cy = parentRect1.height - childRect1.height;
                        child.css({ 'left': cx + 'px', 'top': cy + 'px' });
                        clearTimeout(timeoutUpdatePosition);
                        // Update value x,y of config edit info
                        timeoutUpdatePosition = setTimeout(function (){
                            const tmpConfigEdit = listConfigEdit.find((config)=> config.id === id);
                            if (tmpConfigEdit) {
                                tmpConfigEdit.infoConfig.x = cx;
                                tmpConfigEdit.infoConfig.y = cy;
                                $("#"+id+" input[name='x']").val(parseInt(Math.round(cx)));
                                $("#"+id+" input[name='y']").val(parseInt(Math.round(cy)));
                            }
                        },400);
                    }

                });
            });
            $(window).mouseup(function() {
                child.css({ 'pointer-events': 'all' });
                isMoving = false;
            });
        }
    //     Scroll to an element inside an element
        function scrollToElm(container, elm, duration){
            var pos = getRelativePos(container,elm);
            scrollTo(container, pos.top , duration/1000);  // duration in seconds
        }

        function getRelativePos(container,elm){
            var pPos = container.getBoundingClientRect(), // parent pos
                cPos = elm.getBoundingClientRect(), // target pos
                pos = {};

            pos.top    = cPos.top    - pPos.top + elm.parentNode.scrollTop,
                pos.right  = cPos.right  - pPos.right,
                pos.bottom = cPos.bottom - pPos.bottom,
                pos.left   = cPos.left   - pPos.left;

            return pos;
        }

        function scrollTo(element, to, duration, onDone) {
            var start = element.scrollTop,
                change = to - start,
                startTime = performance.now(),
                val, now, elapsed, t;

            function animateScroll(){
                now = performance.now();
                elapsed = (now - startTime)/1000;
                t = (elapsed/duration);

                element.scrollTop = start + change * easeInOutQuad(t);

                if( t < 1 )
                    window.requestAnimationFrame(animateScroll);
                else
                    onDone && onDone();
            };

            animateScroll();
        }

        function easeInOutQuad(t){ return t<.5 ? 2*t*t : -1+(4-2*t)*t };
    })
</script>
</body>
</html>
