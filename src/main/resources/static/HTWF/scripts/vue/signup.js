Vue.component('signUp', {
    data: function (){
        return{
            title: '',
            firstname:'',
            lastname:'',
            middlename:'',
            username:'',
            email: '',
            countryCode: '',
            phone:'',
            password:'',
            buttonText: i18next.t('signUp'),
            validationMessage: [],
            prefixList: [],
        }
    },
    mounted(){
        axios.get('/init/properties?property=name_prefix')
            .then(response => {
                this.prefixList = response.data;
                console.log("prefix: ", this.prefixList)
            })
            .catch(error => {
                swal("Error!", error.response.data.message, "error");
            })
    },
    watch: {
        email(value) {
            this.email = value
            this.validateEmail(value)
        },
        password(value) {
            this.password = value
            this.minimumLength(value)
            this.mustContainLowerCase(value)
            this.mustContainNumber(value)
            this.mustContainSpecialCharacter(value)
            this.mustContainUppercase(value)
        },
        phone(value) {
            this.phone = value
            this.validatePhone(value)
        },
        countryCode(value) {
            this.countryCode = value
            this.validateCountryCode(value)
        }
    },
    methods: {
        validateEmail(value) {
            if (/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(value)) {
                this.validationMessage.email = ''
            } else if (value === '') {
                this.validationMessage.email = ''
            } else {
                this.validationMessage.email = `${i18next.t('invalidEmail')}`
            }
        },
        minimumLength(value) {
            let difference = 8 - value.length;
            if (value.length > 0 && value.length < 8) {
                this.validationMessage.minimumLength = 'Must be 8 characters! '+ difference + ' characters left' ;
            } else {
                this.validationMessage.minimumLength = '';
            }
        },
        mustContainUppercase(value) {
            if (/[A-Z]/.test(value)) {
                this.validationMessage.upperCase = ''
            } else if (value === '') {
                this.validationMessage.upperCase = ''
            } else {
                this.validationMessage.upperCase = `${i18next.t('upperCase')}`
            }
        },
        mustContainLowerCase(value) {
            if (/[a-z]/.test(value)) {
                this.validationMessage.lowerCase = ''
            } else if (value === '') {
                this.validationMessage.lowerCase = ''
            } else {
                this.validationMessage.lowerCase = `${i18next.t('lowerCase')}`
            }
        },
        mustContainNumber(value) {
            if (/[0-9]/.test(value)) {
                this.validationMessage.number = ''
            } else if (value === '') {
                this.validationMessage.number = ''
            } else {
                this.validationMessage.number = `${i18next.t('oneDigit')}`
            }
        },
        mustContainSpecialCharacter(value) {
            if (/[#?!@$%^&*-]/.test(value)) {
                this.validationMessage.specialCharacter = ''
            } else if (value === '') {
                this.validationMessage.specialCharacter = ''
            } else {
                this.validationMessage.specialCharacter = `${i18next.t('oneSpecialCharacter')}`
            }
        },
        validatePhone(value) {
            if (/^\d{10}$/.test(value)) {
                this.validationMessage.phone = ''
            } else if (value === '') {
                this.validationMessage.phone = ''
            } else {
                this.validationMessage.phone = `${i18next.t('invalidPhone')}`
            }
        },
        validateCountryCode(value) {
            if (/^\+\d{1,3}$/.test(value)) {
                this.validationMessage.countryCode = ''
            } else if (value === '') {
                this.validationMessage.countryCode = ''
            } else {
                this.validationMessage.countryCode = `${i18next.t('invalidCountryCode')}`
            }
        },
        registerUser() {
            this.buttonText = i18next.t('signUpButtonText');
            console.log(this.buttonText)
            var _csrf = $("meta[name='_csrf']").attr("content");
            var _csrf_header = $("meta[name='_csrf_header']").attr("content");
            var self = this;
            var data = {
                firstName: this.title+' '+this.firstname,
                lastName: this.lastname,
                middleName: this.middlename,
                userName: this.username,
                email: this.email,
                password: this.password,
                mobile: this.countryCode+this.phone,

            }
            const request = {
                method: 'POST',
                headers: {[JSON.stringify(_csrf_header).replaceAll('"', "")]: _csrf },
                data: data,
                url: '/init/user/register',
            }
            axios(request)
                .then((response)=> {
                    this.buttonText = i18next.t('signUp');
                    swal("Success!", `${i18next.t('verificationLinkMessage')}`, "success");
                    this.firstname='';
                    this.lastname='';
                    this.middlename='';
                    this.username='';
                    this.email= '';
                    this.phone='';
                    this.password='';

                })
                .catch((error)=> {
                    this.buttonText = i18next.t('signUp');
                    swal("Error!", error.response.data.message, "error");
                });
        },
    },
    template:
        `
<div class="container" style="margin-top: -65px">
            <div class="row gutters">
                <div class="col-xl-9 col-lg-9 col-md-12 col-sm-12 col-12">
                    <div class="card ">
                        <div class="card-body">
                        <form @submit.prevent="registerUser">
                            <div class="row gutters">
                                <div class="col-xl-4 col-lg-4 col-md-4 col-sm-4 col-12">
                                    <div class="form-group">
                                        <label for="" >${i18next.t('selectTitle')}</label>
                                        <select class="form-control" v-model="title" required>
                                            <option disabled value="">${i18next.t('selectTitle')}</option>
                                            <option v-for="prefix in prefixList" :value="prefix">{{ prefix }}</option>
                                        </select>
                                    </div>
                                </div>
                            </div>
                         
                            <div class="row gutters">
                                <div class="col-xl-4 col-lg-4 col-md-4 col-sm-4 col-12">
                                    <div class="form-group">
                                        <label for="fullName">${i18next.t('firstName')}</label>
                                        <input type="text" class="form-control" id="fullName" v-model="firstname" placeholder="${i18next.t('enterFirstName')}" required>
                                    </div>
                                </div>
                                <div class="col-xl-4 col-lg-4 col-md-4 col-sm-4 col-12">
                                    <div class="form-group">
                                        <label for="fullName">${i18next.t('middleName')}</label>
                                        <input type="text" class="form-control" id="middleName" v-model="middlename" placeholder="${i18next.t('enterMiddleName')}">
                                    </div>
                                </div>
                                <div class="col-xl-4 col-lg-4 col-md-4 col-sm-4 col-12">
                                    <div class="form-group">
                                        <label for="fullName">${i18next.t('lastName')}</label>
                                        <input type="text" class="form-control" id="lastName" v-model="lastname" placeholder="${i18next.t('enterLastName')}" required>
                                    </div>
                                </div>
                                <div class="col-xl-4 col-lg-4 col-md-4 col-sm-4 col-12">
                                    <div class="form-group">
                                        <label for="username">${i18next.t('username')}</label>
                                        <input type="text" class="form-control"  id="username" v-model="username" placeholder="${i18next.t('username')}" required>
                                    </div>
                                </div>
                                <div class="col-xl-4 col-lg-4 col-md-4 col-sm-4 col-12">
                                    
                                    <div class="form-group">
                                        <label for="phone">${i18next.t('primaryEmail')}</label>
                                        <input type="email" class="form-control"  id="email" v-model="email" placeholder="${i18next.t('enterPrimaryEmail')}" required>
                                    </div>
                                    <span class="text text-danger text-sm" v-if="validationMessage.email">{{ validationMessage.email }}</span>
                                </div>
                                <div class="col-xl-4 col-lg-4 col-md-4 col-sm-4 col-12">
                                    <div class="form-group">
                                        <label for="password">${i18next.t('password')}</label>
                                        <input type="password" class="form-control" id="password" v-model="password" placeholder="${i18next.t('password')}" required>
                                    </div>
                                    <ul class="text-left">
                                    <span class="text text-danger text-sm" v-if="validationMessage.minimumLength"><li>{{ validationMessage.minimumLength }}</li></span>
                                    <span class="text text-danger text-sm" v-if="validationMessage.lowerCase"><li>{{ validationMessage.lowerCase }}</li></span>
                                    <span class="text text-danger text-sm" v-if="validationMessage.upperCase"><li>{{ validationMessage.upperCase }}</li></span>
                                    <span class="text text-danger text-sm" v-if="validationMessage.number"><li>{{ validationMessage.number }}</li></span>
                                    <span class="text text-danger text-sm" v-if="validationMessage.specialCharacter"><li>{{ validationMessage.specialCharacter }}</li></span>
                                    </ul> 
                                </div>
                                <div class="col-xl-2 col-lg-2 col-md-2 col-sm-2 col-12">
                                    <div class="form-group">
                                        <label for="countryCode">${i18next.t('countryCode')}</label>
                                        <input type="text" class="form-control" id="countryCode" v-model="countryCode" placeholder="${i18next.t('countryCode')}" required>
                                    </div>
                                    <span class="text text-danger text-sm" v-if="validationMessage.countryCode">{{ validationMessage.countryCode }}</span>
                                </div>
                                <div class="col-xl-5 col-lg-5 col-md-5 col-sm-5 col-12">
                                    
                                    <div class="form-group">
                                        <label for="phone">${i18next.t('phone')}</label>
                                        <input type="text" class="form-control" id="phone" v-model="phone" placeholder="${i18next.t('enterPhone')}" required>
                                    </div>
                                    <span class="text text-danger text-sm" v-if="validationMessage.phone">{{ validationMessage.phone }}</span>
                                </div>
                            </div>
                        <div class="row gutters">
                        <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
                                <div class="text-left" style="font-size: 16px; font-weight: bold;">${i18next.t('alreadyHaveAnAccount')}</div><a style="font-size: 16px; font-weight: bold; color: #6f5499" href="/login">${i18next.t('login')}</a>
                            </div>
                            <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
                                <div class="text-right">
                                    <button :disabled="(validationMessage.phone || validationMessage.countryCode || validationMessage.email || validationMessage.minimumLength || validationMessage.lowerCase || validationMessage.upperCase || validationMessage.number || validationMessage.specialCharacter) !== '' "  type="submit" id="submit" name="submit" style=" font-size: 16px; background-color: #6f5499; color: #fff; font-weight: bold" class="btn">{{ buttonText }}</button>
                                </div>
                            </div>
                        </div>
                        </form>
                       
                    </div>
                </div>
            </div>
        </div>
    </div>



`
})
new Vue({ el: "#sign_up"})
