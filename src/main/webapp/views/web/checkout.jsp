<%--
  Created by IntelliJ IDEA.
  User: HUY
  Date: 12/30/2023
  Time: 12:21 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Nai - Checkout</title>
</head>
<body>
<!-- Container for demo purpose -->
<div class="container my-5">

    <!--Section: Design Block-->
    <section class="mb-10">
        <style>
            @media (max-width: 450px) {
                .product-image-2 {
                    width: 100%;
                }
            }
            @media (min-width: 451px) {
                .product-image-2 {
                    width: 100px;
                }
            }
        </style>
        <div class="row">
            <div class="col-lg-6 pe-lg-4 mb-5 mb-lg-0">
                <h5 class="mb-4">Your information</h5>
                <form>
                    <div class="row">
                        <div class="col-md-6">
                            <div class="form-outline mb-4">
                                <input type="text" id="input0x" class="form-control" />
                                <label class="form-label" for="input0x">First name</label>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-outline mb-4">
                                <input type="text" id="input1x" class="form-control" />
                                <label class="form-label" for="input1x">Last name</label>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-6">
                            <div class="form-outline mb-4">
                                <input type="email" id="input2x" class="form-control" />
                                <label class="form-label" for="input2x">Email</label>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-outline mb-4">
                                <input type="tel" id="input3x" class="form-control" />
                                <label class="form-label" for="input3x">Phone</label>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-6">
                            <div class="form-outline mb-4">
                                <input type="text" id="input4x" class="form-control" />
                                <label class="form-label" for="input4x">Address</label>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-outline mb-4">
                                <input type="text" id="input5x" class="form-control" />
                                <label class="form-label" for="input5x">Apartment, suit, etc.</label>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-6">
                            <div class="form-outline mb-4">
                                <input type="text" id="input6x" class="form-control" />
                                <label class="form-label" for="input6x">City</label>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-outline mb-4">
                                <input type="text" id="input7x" class="form-control" />
                                <label class="form-label" for="input7x">Postal code</label>
                            </div>
                        </div>
                    </div>
                    <div class="row mb-5">
                        <div class="col-12">
                            <select class="select">
                                <option value="1">Italy</option>
                                <option value="2">Germany</option>
                                <option value="3">France</option>
                                <option value="4">Netherlands</option>
                                <option value="5">Spain</option>
                                <option value="6">Portugal</option>
                            </select>
                            <label class="form-label select-label">Country</label>
                        </div>
                    </div>
                </form>
                <h5 class="mb-4">Delivery</h5>
                <div class="form-check pb-4 mb-4 border-bottom border-2" style="border-color: hsl(0,0%, 96%) !important;">
                    <input class="form-check-input" type="checkbox" value="" id="flexCheckChecked0x" checked/>
                    <label class="form-check-label" for="flexCheckChecked0x">Same address as above</label>
                </div>
                <div class="mb-5">
                    <div class="form-check mb-3">
                        <input class="form-check-input" type="radio" name="flexRadioDefault0X" id="radio0x" checked/>
                        <label class="form-check-label" for="radio0x">
                            <div>
                                <p class="mb-0">Standard home delivery</p>
                                <p class="text-muted mb-0">Free • Between 3 and 5 business days</p>
                            </div>
                        </label>
                    </div>
                    <div class="form-check mb-3">
                        <input class="form-check-input" type="radio" name="flexRadioDefault0X" id="radio1x"/>
                        <label class="form-check-label" for="radio1x">
                            <div>
                                <p class="mb-0">Express home delivery</p>
                                <p class="text-muted mb-0">10 € • Between 1 and 2 business days</p>
                            </div>
                        </label>
                    </div>
                    <div class="form-check mb-0">
                        <input class="form-check-input" type="radio" name="flexRadioDefault0X" id="radio2x"/>
                        <label class="form-check-label" for="radio2x">
                            <div>
                                <p class="mb-0">Standard pick up location</p>
                                <p class="text-muted mb-0">Free • Between 3 and 5 business day</p>
                            </div>
                        </label>
                    </div>
                </div>
                <h5 class="mb-4">Payment</h5>
                <div class="accordion accordion-flush mb-4" id="accordionFlushExampleX0">
                    <div class="accordion-item border-0">
                        <div class="form-check mb-2">
                            <input class="form-check-input collapsed" type="radio" name="flexRadioDefault6x0"
                                   id="flexRadioDefault17x0" data-mdb-toggle="collapse" data-mdb-target="#flush-collapseOnex0"
                                   aria-expanded="false" aria-controls="flush-collapseOnex0" checked />
                            <label class="form-check-label" for="flexRadioDefault17x0">
                                <div>
                                    <p class="mb-1">Card</p>
                                    <p class="text-muted mb-0">Credit or debit card</p>
                                </div>
                            </label>
                        </div>
                        <div id="flush-collapseOnex0" class="accordion-collapse collapse" aria-labelledby="flush-headingOnex0"
                             data-mdb-parent="#accordionFlushExampleX0">
                            <div class="accordion-body">
                                <div class="form-outline mb-4">
                                    <input type="text" id="cardNuumber1u" class="form-control" />
                                    <label class="form-label" for="cardNuumber1u">Card number</label>
                                </div>
                                <div class="row">
                                    <div class="col-md-8">
                                        <div class="form-outline mb-4">
                                            <input type="text" id="cardExp2u5" class="form-control" />
                                            <label class="form-label" for="cardExp2u5">Expiration date (MM/YY)</label>
                                        </div>
                                    </div>
                                    <div class="col-md-4">
                                        <div class="form-outline mb-4">
                                            <input type="text" id="cardCVC2u5" class="form-control" />
                                            <label class="form-label" for="cardCVC2u5">CVC</label>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="accordion-item">
                        <div class="form-check">
                            <input class="form-check-input collapsed" type="radio" name="flexRadioDefault6x0"
                                   id="flexRadioDefault16x0" data-mdb-toggle="collapse" data-mdb-target="#flush-collapseTwox0"
                                   aria-expanded="false" aria-controls="flush-collapseTwox0" />
                            <label class="form-check-label" for="flexRadioDefault16x0"> PayPal </label>
                        </div>
                        <div id="flush-collapseTwox0" class="accordion-collapse collapse" aria-labelledby="flush-headingTwox0"
                             data-mdb-parent="#accordionFlushExampleX0">
                            <div class="accordion-body">
                                <p class="mb-0">PayPal External Payment Method</p>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="pt-1 mb-3">
                    <button class="btn btn-dark btn-lg px-5">Pay order</button>
                    <p class="small text-muted mt-4 mb-0">By clicking "Proceed to PayPal" I confirm I have read <a href="#!">Privacy Notice</a> and <a href="#!">Cookie Notice</a>. I agree to the <a href="#!">terms & conditions</a> of the store. "I also accept that the store will process my personal data to manage my order, in accordance with the store's <a href="#!">privacy notice</a>"</p>
                </div>
            </div>
            <div class="col-lg-6 ps-lg-4">
                <div class="d-md-flex border-bottom border-2 pb-4 mb-4" style="border-color: hsl(0,0%, 96%) !important;">
                    <div class="flex-shrink-0 mb-4 mb-md-0">
                        <div class="bg-image hover-overlay ripple shadow-4 rounded-5" data-mdb-ripple-color="light">
                            <a href="#!">
                                <img src="https://mdbootstrap.com/img/Photos/new-templates/img57.jpg" class="product-image-2" alt="Item 1">
                                <div class="mask" style="background-color: rgba(255, 255, 255, 0.2);"></div>
                            </a>
                        </div>
                    </div>
                    <div class="flex-grow-1 ms-md-3 d-flex justify-content-between h-100">
                        <div>
                            <p class="h6 mb-2">A set of cosmetics for the body and face</p>
                            <p class="mb-1">€49.00</p>
                            <p class="mb-0">Quantity: <span>1</span></p>
                        </div>
                        <div class="d-flex flex-column ms-3">
                            <p class="mb-0"><a href="#!">Edit</a><span class="mx-2" style="color: hsl(0,0%,90%);">|</span><a href="#!">Remove</a></p>
                            <p class="mt-auto mb-0">€49.00</p>
                        </div>
                    </div>
                </div>
                <div class="d-md-flex border-bottom border-2 pb-4 mb-4" style="border-color: hsl(0,0%, 96%) !important;">
                    <div class="flex-shrink-0 mb-4 mb-md-0">
                        <div class="bg-image hover-overlay ripple shadow-4 rounded-5" data-mdb-ripple-color="light">
                            <a href="#!">
                                <img src="https://mdbootstrap.com/img/Photos/new-templates/img55.jpg" class="product-image-2" alt="Item 2">
                                <div class="mask" style="background-color: rgba(255, 255, 255, 0.2);"></div>
                            </a>
                        </div>
                    </div>
                    <div class="flex-grow-1 ms-md-3 d-flex justify-content-between h-100">
                        <div>
                            <p class="h6 mb-2">Black ceramic mug</p>
                            <p class="mb-1">€19.00</p>
                            <p class="mb-0">Quantity: <span>2</span></p>
                        </div>
                        <div class="d-flex flex-column ms-3">
                            <p class="mb-0"><a href="#!">Edit</a><span class="mx-2" style="color: hsl(0,0%,90%);">|</span><a href="#!">Remove</a></p>
                            <p class="mt-auto mb-0">€38.00</p>
                        </div>
                    </div>
                </div>
                <div class="d-md-flex border-bottom border-2 pb-4 mb-4" style="border-color: hsl(0,0%, 96%) !important;">
                    <div class="flex-shrink-0 mb-4 mb-md-0">
                        <div class="bg-image hover-overlay ripple shadow-4 rounded-5" data-mdb-ripple-color="light">
                            <a href="#!">
                                <img src="https://mdbootstrap.com/img/Photos/new-templates/img56.jpg" class="product-image-2" alt="Item 3">
                                <div class="mask" style="background-color: rgba(255, 255, 255, 0.2);"></div>
                            </a>
                        </div>
                    </div>
                    <div class="flex-grow-1 ms-md-3 d-flex justify-content-between h-100">
                        <div>
                            <p class="h6 mb-2">Notebook and planner set</p>
                            <p class="mb-1">€59.00</p>
                            <p class="mb-0">Quantity: <span>1</span></p>
                        </div>
                        <div class="d-flex flex-column ms-3">
                            <p class="mb-0"><a href="#!">Edit</a><span class="mx-2" style="color: hsl(0,0%,90%);">|</span><a href="#!">Remove</a></p>
                            <p class="mt-auto mb-0">€59.00</p>
                        </div>
                    </div>
                </div>
                <div class="d-flex justify-content-between">
                    <p class="mb-2">Order value</p>
                    <p class="mb-2">€146.00</p>
                </div>
                <div class="d-flex justify-content-between border-bottom border-2 pb-2 mb-4" style="border-color: hsl(0,0%, 96%) !important;">
                    <p>Delivery</p>
                    <p>free</p>
                </div>
                <div class="d-flex justify-content-between">
                    <p class="h5 mb-5">Total</p>
                    <p class="h5 mb-5">€146.00</p>
                </div>
                <div class="small">
                    <p class="text-muted mb-4">Our returns are free and easy. If something isn't quite right, you have 14 days to send it back to us. Read more in our <a class="#!">return and refund policy</a>.</p>
                    <p class="text-warning mb-4">FREE SHIPPING ON ORDERS OVER €100 AND FREE RETURNS</p>
                    <p class="text-muted mb-0">In accordance with our <a class="#!">Privacy Notice</a>, if you are signed in to your store account, we will share personal data from your account with company for order checkout and payment purposes.</p>
                </div>
            </div>
        </div>
    </section>
    <!--Section: Design Block-->

</div>
<!-- Container for demo purpose -->
</body>
</html>
