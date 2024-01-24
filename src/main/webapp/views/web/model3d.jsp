<%--
  Created by IntelliJ IDEA.
  User: HUY
  Date: 1/22/2024
  Time: 10:24 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>

</head>
<body>
    <div class="canvasDiv" style="position : absolute;top :0;right:0;left:0;bottom:0;"></div>
<%--    <script type="module" src="https://unpkg.com/three@0.160.1/build/three.module.js">--%>
<%--    </script>--%>
<%--    <script type="module" src="https://unpkg.com/three@0.160.1/examples/jsm/controls/OrbitControls.js">--%>
<%--    </script>--%>
<script type="module">
    import * as THREE from 'https://cdn.skypack.dev/three';
    import {OrbitControls} from 'https://cdn.skypack.dev/three/examples/jsm/controls/OrbitControls.js';
    window.addEventListener("DOMContentLoaded",function () {
        const render = new THREE.WebGL1Renderer({
            preserveDrawingBuffer: true,
        });
        render.shadowMap.enabled = true;
        render.setSize(window.innerWidth, window.innerHeight);
        // resize canvas
        window.addEventListener('resize', () => {
            camera.aspect = window.innerWidth / window.innerHeight;
            camera.updateProjectionMatrix();
            render.setSize(window.innerWidth, window.innerHeight);
        });
        $('.canvasDiv').append(render.domElement);
        render.setClearColor(0xebebeb);
        const scene = new THREE.Scene();
        const camera = new THREE.PerspectiveCamera(
            75,
            window.innerWidth / window.innerHeight,
            0.01,
            1000
        );
        camera.position.set(-10, 15, -7);
        const controls = new OrbitControls(camera, render.domElement);
        controls.minDistance = 10;
        controls.maxDistance = 30;
        controls.enablePan = false;
    })

</script>
</body>
</html>
