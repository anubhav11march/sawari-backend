const resources = {
  en: {
    translation: {
      signUp: 'Sign-up',
      login: 'Login',
      home:'Home',
      menu:'Menu',
      upload:'Upload',
      iNeedHelp:'I need help with my',
      file: 'File',
      noOfFigure:'Number of Figures',
      faqq:'FAQs',
      pricing:'Pricing',
      loginSignup:'Login/Signup',
      dashboard:'Dashboard',
      uploadFile:'Upload File',
      trackFile:'Track File',
      orderDetails:'Order Details',
      profile:'Profile',
      findFile: 'Find File',
      newFile: 'New File',
      sNo: 'SNo.',
      topic: 'I need help with my',
      category: 'Category',
      uploadDate: 'Upload Date',
      requestServeDate: 'Request Serve Date',
      edit: 'Edit',
      signUpButtonText: 'Signing Up....',
      firstName: 'First (Given) Name',
      middleName: 'Middle Name (Optional)',
      lastName: 'Last (Family) Name',
      username: 'Username',
      primaryEmail: 'Primary E-Mail Address',
      phone: 'Phone',
      password: 'Password',
      personalDetails: 'Personal Details',
      enterFirstName: 'Enter First Name',
      enterMiddleName: 'Enter Middle Name (optional)',
      enterLastName: 'Enter Last Name',
      enterPhone: 'Enter Phone Number',
      mobile: 'Mobile',
      enterMobile: 'Enter Mobile Number',
      enterPrimaryEmail: 'Enter Primary Email',
      secondaryEmail: 'Secondary E-Mail',
      enterSecondaryEmail: 'Enter Secondary Email',
      address: 'Address',
      enterAddress: 'Enter Address',
      city: 'City',
      state: 'State',
      country: 'Country',
      postalCode: 'Postal Code',
      education: 'Education',
      institution: 'Institution',
      enterInstitution: 'Enter Institution',
      department: 'Department',
      enterDepartment: 'Enter Department',
      cancel: 'Cancel',
      update: 'Update',
      editFile: 'Edit File',
      fileUpload: 'File Upload',
      description: 'Description',
      enterEmail: 'Enter Your Email',
      confirmPassword: 'Confirm Password',
      invalidEmail: 'Invalid Email Address',
      upperCase: 'Atleast one upper case',
      lowerCase: 'Atleast one lower case',
      oneDigit: 'Atleast one digit',
      oneSpecialCharacter: 'Atleast one special character',
      invalidPhone: 'Invalid Phone Number',
      verificationLinkMessage: 'The verification link has been sent to your email. Please verify your account',
      passwordResetMessage: 'Password Reset link has been sent to your email. Please Reset Your Password.',
      passwordConfimation: 'The password confirmation does not match.',
      profileUpdateMessage: 'Profile Updated Successfully',
      action: 'action',
      type: 'type',
      modifyDate: 'Modify Date',
      uploadBy: 'Upload By',
      downloadFile: 'Download File',
      language: 'Language',
      wordsCount: 'Words Count',
      servicesIWant: 'Services I want',
      reports: 'Reports',
      status: 'Status',
      fileId: 'File Id',
      reportId: 'Report Id',
      orderInfo: 'Order Info',
      orderDate: 'Order Date',
      expiryDate: 'Expiry Date',
      selectedServices: 'Selected Services',
      estimatedAmount: 'Estimated Amount',
      remark: 'Remark',
      transactionId: 'Transaction Id',
      totalAmountPaid: 'Total Amount Paid',
      dateOfPayment: 'Date of Payment',
      paymentMode: 'Payment Mode',
      uploadSnapOfPaymentProof: 'Upload Snap of Payment Proof',
      order: 'Order',
      reportType: 'Report Type',
      proposedServices: 'Proposed Services',
      currentPage: 'Current Page',
      payment:'Payment',

      ourServices:'Our Services',
      ourServicesContent:'Our services help researchers and specialists in the field of medicine and natural sciences to write their scientific papers, theses as well as research funding projects and other services. Our services provide an integrated set that includes English language editing, academic writing, scientific forms services, and research preparation carried out by specialists and experts in the field of medicine and natural sciences, which will support the acceptance of your research or scientific project and present it in a professional scientific way that highlights your scientific efforts in the scientific community and increases opportunities success in your career.',

      ourServicesIntro: 'Alpha Editorial Science Hub \n We help authors to write their manuscript, thesis, grant and others. Our services supported by the science and medical professionals have the full suite of services that include English Editing, Academic Writing, Figure and Tables formatting Services, Manuscript preparation services and others to increase your manuscript acceptance and extend the impact of your published research.',
      englishLanguageService: 'English Language Editing is performed by highly experienced English native speakers expert in your research area. Academic English reviewers will ensure that your ideas are well communicated and presented clearly and scientifically. ',
      scientificEditingService: '*Assigns highly qualified field-specific PhD editors to achieve the editing set by high peer-reviewed journals standard\n' +
          '*Examines thoroughly all your scientific contents and evaluates each aspect\n' +
          '*Carefully edits your work and makes it ready for publishing quality',
      academicIllustrationService: 'Present your essential findings in compelling and visually appealing illustrations to promote your work on social media, conferences, interviews or journals. Graphical abstract helps you present your hard work to the readers in a glance.\n',
      figureFormattingService: '*Formats your figures to match your target journals which achieves best layout and presentation of your important results ',
      graphicalAbstractService: '*Graphical abstract helps you present your hard work to the readers at a glance\n' +
          'Presents your important finding story in a simple and compelling visual illustration to promote your work in social media conferences or interviews',
      journalRecommendationService: 'Based on your research findings, we will include a detailed report on three to four recommended journals by experts within 3-4 days. This selection matches your research topic and is based on the journals scope, impact factor, and other factors. We do not peer-review and cannot guarantee your manuscripts acceptance.',
      conferencePosterService: 'With our Conference Poster Creation service, we let your research stand high. Based on your supporting data, published articles and supporting files, academic experts in the fieldwork creates a high-impact, visually appealing posters for you to present your findings in conferences or meetings. You will get a high-resolution and editable PPTX and print-ready image file formatted according to the conference poster guidelines.',
      researchNewsStoryService: 'Using this service, you can confidently share your newsworthy findings with mass/social media. Besides getting a word file with a succinct note of 300-500 words, we will also send you the keywords, hashtags and link to your article DOI. Moreover, you will get suggested description text for social media usage like LinkedIn, Twitter, etc. Share your work with a wider audience with this service.',
      testimonialName1:'Dr. Wilson\n',
      testimonialContent1:'AESH participated in improving the contents of my scientific research draft and presenting my research work in a more elegant and scientifically sound manner. In the end, I got my article accepted into a magazine with a much higher impact factor than I ever expected.\n',
      testimonialName2:'Dr. Ahmed\n',
      testimonialContent2:'I was interested in presenting my work in an international conference and I needed some experts in my research field to help me present my important work in a more professional manner and summarizing all my work and attracting the attention of the audience in a short time. AESH was the right choice to do all the work and bring that out in an excellent presentation. My sincere appreciation for their efforts.\n',
      testimonialName3:'Prof. Xu Weili\n',
      testimonialContent3:'AESH are very quick in their work, very well priced and do an excellent job. As a scholar I have done many English editing for a number of companies and AESH has been the best. I think journal reviewers will no longer tell us that my scientific draft should be reviewed in English by native speakers.\n',
      whyOurService:'We welcome you to the home of professional, scientific writers. Our aim is to support authors in publishing their research with complete article preparation and writing support services.\n',
      confidentialityContent:'We adhere to a strict privacy policy that guarantees 100% confidentiality and allows researchers around the world to submit their work to us with complete confidence. All editors, reviewers and staff are obligated to sign a commitment to the privacy and guarantee of clients\' work.\n',
      qualityGuaranteeContent:'You can count on us for excellence, expertise, and responsiveness with respect to all our services. If we delay your work or your edited manuscript is rejected for language-quality or structure,  we offer free re-editing or the money is fully refunded.\n',
      aesh:'Alpha Editorial Science Hub',
      aeshPunch:'Transforms the vision into reality',
      aeshContent:'Now our services supported by the science and medical professionals have the full suite of services\n  that include Academic Translation, Scientific Writing, Figure Services, Manuscript Formatting and\n others to increase your manuscript acceptance and to extend the impact of your published research.',
      getStarted:'Get Started',
      contactUs:'Contact Us',
      step1:'Step-1',
      step2:'Step-2',
      step3:'Step-3',
      step4:'Step-4',
      step1Content:'Select the service you need for your work',
      step2Content:'Upload your manuscript and complete the order details',
      step3Content:'Work with our subject-area expert(s) to fulfil your request',
      step4Content:'Complete payment and download your final files',
      services:'Services',
      our:'Our',
      viewAllServices:'View all services',
      englishLanguageEditing:'English Language Editing',
      scientificEditing:'Scientific Editing',
      academicIllustration:'Academic Illustration',
      conferencePosterCreation:'Conference Poster Creation',
      researchNewsStories:'Research News Stories',
      journalRecommendation:'Journal Recommendation',
      figureFormatting:'Figure Formatting',
      graphicalAbstract:'Graphical Abstract Service',
      readMore:'Read more',
      whyOurServices:'Why Our Services',
      confidentiality:'Confidentiality',
      qualityGuarantee:'Quality Guarantee and on-time delivery',
      ourTestimonials:'Our testimonials',
      whatOurClientsThink:'What our clients think',
      testimonialContent:'Lorem ipsum dolor sit amet consectetur adipiscing elitsed do eiusmod tempor incididunt utlabore et\n dolore magna aliqua.\n Utenim ad minim veniam quis nostrud exercitation ullamco .',
      englishLanguageEditingPageContent:`<p><b>We authentically edit the English language errors.</b></p>
<ul> 
<li>Paraphrasing, spelling, punctuation and grammar correction</li>
<li>Edit the structure of sentences and ensure the clarity and smoothness of the contents of the work</li>
<li>Reducing the number of words and examining the contents of scientific research</li>
<li>Provide a detailed description of common mistakes found in your writing that may help you get ideas for improving your work in the future</li>
</ul><br>
<p>In this service, experts in your research field will ensure that the fundamentals and contents of your research work are clearly stated, and the sequence of work contents is maintained with reference to content confusion.<br></p>
<p><b>Note:</b> In this service, the editors will not improve the contents of the research, restructure the research draft, or prepare it for publication in a journal</p>
`,
      scientificEditingPageContent:`<p><b>We share with you the stressful  journey of your reasearch and publication</b></p>
<p>
In this service, an expert in your area will write your manuscript and preparation for publication and provide strategic comments for your work (manuscript, grant, or thesis) in journals related to natural sciences like medicine and biology. The editing quality will be in accordance with the international scientific standards that will let you submit your manuscript with confidence. The editors who are experts in your field are always in your reach to help you to take the burdens of your Scientific Writing and thoroughly edit your manuscript. In addition, an overall report and a detailed summary of your work will be provided.</p> 
<p><b>The Scientific write-up will be assessed by specialized scientific editors</b></p>
<p><ul>
<li>Enthusiastic and qualified scientific writers with a Ph.D. who are committed to taking scientific writing as a career will carefully assess your work and meticulously write your manuscript. </li>
<li>Your work will be carefully assessed and find the issues in your project and give priorities to fix the errors and provide a detailed report that describes all issues in your work. </li>
</ul></p>

<p><b>Guarantee of our Quality and continued support</b></p>
<p>
The work guarantee is extended for six months, where it can be re-edited for free. 
Note: this will be invalid if our work had been edited by someone else. 
If the work is rejected by a journal due to the English or the structure of the manuscript, we would redo the editing for free (Although we have never experienced such cases).</p>

`,
      researchNewsStoriesPageContent:`<p><b>It is impressive to present  your new research to the general audience,, </b></p>
<p>
In this service, your work will be summrized and transformed into a simplified and interesting story that highlights your work for public readers. Publishing your distinguished work among the public in the form of news that can be published in newspapers or social media, and includes hashtags and keywords to facilitate finding of your research, as well as lionks related to your original work. Publishing your scientific achievements in the form of an interesting and expressive story will make the audience and society aware of your new discoveries in a simple and understandable language.
How the scientific story works: Submit your research that has been accepted in a scientific journal to be formulated in a scientific story or news.
Experts and scientific editors specialized in your field of research and interested in writing scientific stories will make a simple and interesting summary that will inform you of all the results of your work and highlight the important discoveries in your research
The senior editor should review and verify the accuracy of the abstract by specialists in your research field
You can download the news story as a Word file</p>
`,
      academicIllustrationPageContent:`<p>Present your essential findings in compelling and visually appealing illustrations to promote your work on social media, conferences, interviews or journals. Graphical abstract helps you present your hard work to the readers at a glance. </p>`,
      conferencePosterCreationPageContent:`<p><b>Present your scientific achievements in scientific conferences and international forums
Why we may need a service to create scientific posters?</b></p>
<p>
Those interested and scientific specialists, during scientific conferences and exhibitions, are interested in visiting the exhibition of scientific posters, evaluating the importance of research and creating relationships with experts in the field of your scientific research.
The accuracy of your research summary and presentation of the concepts of your scientific results as well as the technical presentation and coordination of the contents of the poster plays a role in your exposure to the scientific community and gives you the opportunity to discuss your important discoveries with experts in your field.
With our conference poster creation service, we let your research stand high. Based on your supporting statements, published articles, and supporting files, academic experts in the fieldwork create high-impact, visually appealing posters to display your results at conferences or meetings. You will receive a high-resolution, editable PPTX file and a print-ready image file formatted according to the conference poster guidelines.
Experts and technicians in your field are ready to modify the poster until you are completely satisfied and in compliance with the requirements of your scientific conference.</p>
`,
      figureFormattingPageContent:`<p><b>Take the help of our scientific and technical experts to deal with the technical details to save your precious time and effort.</b></p>
<p>
Presenting the scientific forms in your research is very important for presenting the results of your research and creating a quick impression about your research by the editors and reviewers of the journal.</p>
<p><b>Scientific formatting requirements</b></p>
<p>
The authors of the research should provide the technical editor specialist in your research area and designers with the editable figure files, as well as the details and contents of the figures and targeted journal for publication.
The scientific designers will coordinate the shape according to the guidelines of the journal’s artwork and review it by professional experts in the field of your scientific research to ensure its accuracy and consistency with the related work.</p>

<p><b>What you will get: </b></p>
<p>
Provide the authors with an editable file as well as a file in the required format and quality required by the journal and the scientific designers will coordinate the shape according to the guidelines of the journal’s artwork and review it by specialists in the field of your scientific research to ensure its accuracy and conformity to the required work. 
To prepare the figures for journals, editors will consider your chosen journal’s specifications like resolution, layout size, fonts, color, space, scale, size of fonts, etc. In this service, our expert editors will ensure that your figures or table are in coherence with the journal’s requirements. 
You can submit your figure/s or table to our editors and ask for a quote. 
</p>`,
      journalRecommendationPageContent:`<p><b>Are you confused how to choose a scientific journal suitable for your research among thousands of journals
We will be with you step by step to prepare your scientific research from amendment to submission to the journal.
Why do you need a specialized scientific expert to provide advice in choosing a suitable journal for your research?</b></p>
<p>
Was your paper rejected because it did not match the journal's scope?
The scientific community contains thousands of scientific journals, and it is very common to reject research because it does not match the scope of the journal, which may waste your time and effort. Several criteria are needed to be met before submitting research to a specific journal, such as the journal’s scope, impact factor, journal index, review period, publishing house and other criteria.
With the help of specialists in the field of your scientific research, your research will be studied, compared with scientific research in your field, your research will be evaluated, the results will be evaluated, the authors’ requirements will be selected, three to four scientific journals will be selected with a comprehensive summary of all the journal’s criteria and a simplified explanation of the reason for choosing the journal in a report attached to your research.
Note: The service of journal recommendation will save your time and effort and increase the possibility of accepting your research, but it does not guarantee the acceptance of the research by the journal because this depends largely on the quality of the research and the decision of the journal editors based on several factors.
</p>
`,
      graphicalAbstractPageContent:`<p><b>Capture the audience's attention with a professional and expressive illustration
Why do you need an academic illustration for your research?</b></p>
<p>
To display your research data and concepts, you need an illustration that explains the complex and long contents of your research in a form that summarizes all your research information into an amazing and eye-catching artwork that needs scientific and technical experts specialized in your research field. Specialists in your research field interested and professionals in scientific illustrations will make an illustration that fits the instructions of international scientific journals in terms of quality and ability to present the contents and concepts of your research.
The steps involved in making an academic illustration</p>
<p><ul>
<li>Provide us with a hand-drawn picture or model diagrams that may be attached to a summary of your scientific draft and a discussion with a specialist in your field to clarify your requirements</li>
<li>Your project is delivered to professionals in scientific drawing, taking into account the guidelines of the scientific journal</li><ul>
It is reviewed by the work heads and experts in your scientific field to ensure and investigate the accuracy of the work from a technical and scientific point of view
Submit the research in the form of a PDF file or another copy as requested by the authors</p>
<p>
Note: Our technical and scientific experts are fully prepared to revise the illustration until you are fully satisfied with the work even months after the work has been delivered. This does not include the format that has been modified by another party</p>

`,
      aboutUs:'About us',
      aboutUsTag:'We must let go of the life we planned, so as to accept the one that is waiting for us.\n',
      aboutUsContent:`<p>We welcome you to the world of professional natural sciences and medical writers and editors. Our goal is to support authors in publishing their research with complete article preparation and writing support services. Here at ASEH, we are committed to saving your valuable time and efforts and helping authors succeed in their profession. Now our services backed by science and medical professionals have a full range of services that include Academic Editing, English Language Editing, Conference  Poster Creation etc., to enhance the scope of acceptance of your projects and scientific research and expand the impact of your published research.
</p>`,
      faq:'Frequently asked questions',
      faqTag:'Listed questions and answers, all supposed to be commonly asked in some context, and pertaining to a particular topic.',
      generalQuestions:'General questions',
      q1:'Can I ask my editor questions regarding the modification of my research or scientific project?',
      ans1:'Of course the author can ask the editor about his work after the editing process is complete. You can communicate with us through the Contact Us service.',
      q2:'How is academic editing different from English language editing?',
      ans2:'English language editing will focus on phrasing, word selection, errors, grammar, punctuation and other language improvements by English language experts. While scientific editing will ensure scientific contents and ensure that your work is scientifically edited by an expert in your field.',
      q3:'Can I get an invoice for my order?',
      ans3:'When you upload your files, you will receive an online invoice.',
      q4:'Can I pay in my local currency?',
      ans4:'Major currencies are accepted. Please select your currency when you upload your files.',
      q5:'Can you format my references and which reference manager software do you support?',
      ans5:'Yes, we format references and currently, we support Endnote software.',
      q6:'If my manuscript contains figures and tables, do I need to format them?',
      ans6:'If you opt for Scientific Editing Service, the formatting will be included. If not, you can opt for our Figure Formatting service, which can help you prepare the tables and figures.',
      q7:'Is formatting different from editing?',
      ans7:'Yes, expert in Figure Formatting will focus on the figure, table, and ensure all manuscript contents are in agreement with the journal\'s author instructions. On the other hand, the designer will not attend any English language editing.',
      q8:'Is my document safe and secure with AESH?',
      ans8:'AESH maintains a secure website and all our staff have to sign a confidentiality agreement to absolutely protect your work.',
      q9:'How do I get the exact word count?',
      ans9: 'Firstly, you remove all the words that are not related to your work such as figures tables and references.',
      downloadReport: 'Download Report',
      selectServices:'Select Services',
      checkout:'Checkout',
      getQuote: 'Get Quote',
      moreInfo: 'More Info',
      resetPassword: 'Reset Password',
      selectTitle: 'Select Title',
      countryCode: 'Country Code',
      invalidCountryCode: 'Invalid Country Code',
      alreadyHaveAnAccount:'Already have an account?',
      dontHaveAnAccount:'Don\'t have an account?',
      forgotPassword:'Forgot your password?',
      areYouSureLogout:'Are you sure to logout?',
      logout:'Logout',
      resendToken: 'Resend Verification Token?',
      token: 'Resend',
      action: 'action',
      goBack: 'Go Back',
      youCanTrustUs:'You can trust us',
      youCanTrustUsContent:'Our services are handled by highly experienced and enthusiastic scientific editors who are matched to your research subject and continually reviewed for quality to ensure ASEH standards. We offer confidentiality, and integrity combined with a variety of services that will help you to present and promote your work efficiently and professionalism. \n',
    },
  },
  ar: {
    translation: {
      signUp: 'اشتراك',
      login: 'تسجيل الدخول',
      home:'الصفحة الرئيسية',
      menu:'قائمة',
      upload:'تحميل',
      iNeedHelp:'أحتاج إلى مساعدة في بلدي',
      file: 'ملف',
      noOfFigure:'عدد الأشكال',
      faqq:'أسئلة وأجوبة',
      pricing:'التسعير',
      loginSignup:'الدخول التسجيل فى الموقع',
      dashboard:'لوحة القيادة',
      uploadFile:'رفع ملف',
      trackFile:'ملف المسار',
      orderDetails:'تفاصيل الطلب',
      profile:'حساب تعريفي',
      findFile: 'إيجاد ملف',
      newFile: 'ملف جديد',
      sNo: 'رقم سري',
      topic: 'عنوان',
      category: 'فئة',
      uploadDate: 'تاريخ الرفع',
      requestServeDate: 'تاريخ تقديم الطلب',
      edit: 'تعديل',
      signUpButtonText: 'توقيع....',
      firstName: 'أول اسم منحته',
      middleName: 'الاسم الأوسط (اختياري)',
      lastName: 'اسم العائلة (العائلة)',
      username: 'اسم المستخدم',
      primaryEmail: 'عنوان البريد الإلكتروني الرئيسي',
      phone: 'هاتف',
      password: 'كلمة المرور',
      personalDetails: 'تفاصيل شخصية',
      enterFirstName: 'أدخل الاسم الأول',
      enterMiddleName: 'أدخل الاسم الأوسط (اختياري)',
      enterLastName: 'إدخال اسم آخر',
      enterPhone: 'أدخل رقم الهاتف',
      mobile: 'متحرك',
      enterMobile: 'أدخل المحمول',
      enterPrimaryEmail: 'أدخل البريد الإلكتروني الأساسي',
      secondaryEmail: 'البريد الإلكتروني الثانوي',
      enterSecondaryEmail: 'أدخل البريد الإلكتروني الثانوي',
      address: 'تبوك',
      enterAddress: 'أدخل العنوان',
      city: 'مدينة',
      state: 'حالة',
      country: 'بلد',
      postalCode: 'رمز بريدي',
      education: 'تعليم',
      institution: 'مؤسسة',
      enterInstitution: 'أدخل المؤسسة',
      department: 'القسم',
      enterDepartment: 'أدخل القسم',
      cancel: 'إلغاء',
      update: 'تحديث',
      editFile: 'تعديل ملف',
      fileUpload: 'تحميل الملف',
      description: 'وصف',
      enterEmail: 'أدخل بريدك الإلكتروني',
      confirmPassword: 'تأكيد كلمة المرور',
      invalidEmail: 'عنوان البريد الإلكتروني غير صالح',
      upperCase: 'حالة كبيرة واحدة على الأقل',
      lowerCase: 'حالة صغيرة واحدة على الأقل',
      oneDigit: 'رقم واحد على الأقل',
      oneSpecialCharacter: 'على الأقل شخصية خاصة واحدة',
      invalidPhone: 'رقم الهاتف غير صحيح',
      verificationLinkMessage: 'تم إرسال رابط التحقق إلى بريدك الإلكتروني. يرجى التحقق من حسابك',
      passwordResetMessage: 'تم إرسال رابط إعادة تعيين كلمة المرور إلى بريدك الإلكتروني. يرجى إعادة ضبط كلمة المرور الخاصة بك',
      passwordConfimation: 'تأكيد كلمة المرور غير متطابق.',
      profileUpdateMessage: 'تم تحديث الملف الشخصي بنجاح',
      action: 'عمل',
      type: 'اكتب',
      modifyDate: 'تعديل التاريخ',
      uploadBy: 'تحميل بواسطة',
      downloadFile: 'تحميل الملف',
      language: 'لغة',
      wordsCount: 'عدد الكلمات',
      servicesIWant: 'الخدمات التي أريدها',
      reports: 'التقارير',
      status: 'الحالة',
      fileId: 'معرف الملف',
      reportId: 'معرّف التقرير',
      orderInfo: 'معلومات الطلب',
      orderDate: 'تاريخ الطلب',
      expiryDate: 'تاريخ الانتهاء',
      selectedServices: 'خدمات مختارة',
      estimatedAmount: 'القيمة المقدرة',
      remark: 'ملاحظة',
      transactionId: 'رقم المعاملة',
      totalAmountPaid: 'إجمالي المبلغ المدفوع',
      dateOfPayment: 'تاريخ السداد',
      paymentMode: 'طريقة الدفع',
      uploadSnapOfPaymentProof: 'تحميل لقطة من إثبات الدفع',
      order: 'ترتيب',
      reportType: 'نوع التقرير',
      proposedServices: 'الخدمات المقترحة',
      payment:'دفع',

      ourServices:'خدماتنا',
      ourServicesContent:'مساعدة الباحثين والمتخصصين في مجال الطب والعلوم الطبيعية في كتابة أبحاثهم العلمية و أطروحاتهم الدراسية  وكذلك مشاريع تمويل الأبحاث وغيرها من الخدمات،  مع تقديم مجموعة خدمات متكاملة  تشمل تحرير اللغة الإنجليزية ، والكتابة الأكاديمية ، وخدمات الأشكال العلمية، وإعداد الأبحاث بواسطة متخصصين وخبراء في مجال الطب والعلوم الطبيعية التى من شأنها دعم قبول بحثك و مشروعك العلمي وعرضه بطريقة علمية واحترافية تبرز الجهود العلمية في أوساط المجتمع العلمي ورفع كفاءة فرص النجاج في حياتك المهنية.\n',
      ourServicesIntro: 'مساعدة الباحثين في كتابة أبحاثهم, الرسائل العلمية, التقديم على دعم مالى وغيرها. الآن خدماتنا تتم عبر دعم من قبل المتخصصين في العلوم والطب لديها مجموعة متكاملة من الخدمات التي تشمل التعديلات الأكاديمية والكتابة العلمية وخدمات الأشكال العلمية وتنسيق مسودات البحث وغيرها لزيادة قبول بحثك من قبل المجلات العلمية وتوسيع نشر  بحثك المنشور بصورة ملفتة.\n',
      englishLanguageService: 'تعديل وتحرير اللغة الإنجليزية العلمية لبحثك بواسطة ناطقين رسميين لديهم خبرة وكفاءة عالية تخصصية في مجال البحث الأكاديمي تضمن توصيل رسالة بحثك وعرضها بشكل علمي واضح.\n',
      scientificEditingService: 'خدمة التحرير العلمي بواسطة خبراء في مجال بحثك يشمل فحص المحتوى العلمي وتقييمه من كل الجوانب. بحيث يتم إعتماد محرر دكتوراه متخصص في مجال بحثك لتحقيق المعايير المطلوبة في المجلات العلمية المحكمة. وتصحيح البحث بصورة دقيقة والتأكد من جاهزيته للنشر.\n',
      academicIllustrationService: 'أعرض اكتشافاتك الهامه في قصه قصيرة على شكل رسم توضيحي مرئى بسيط وجذاب لعرض عملك في المؤتمرات العلمية أو المقابلات أو على وسائل التواصل الإجتماعى\n',
      figureFormattingService: 'تعديل وتنسيق الأشكال في  مسودة البحث حسب  متطلبات المجلة العلمية المراد النشر بها. وهذا سوف يساعدك الحصول على أفضل تخطيط وعرض لنتائجك المهمة.\n',
      graphicalAbstractService: 'عرض اكتشافاتك الهامة في قصه قصيرة على شكل رسم توضيحي مرئى بسيط وجذاب لعرض عملك في المؤتمرات العلمية أو المقابلات أو على وسائل التواصل الإجتماعى\n',
      journalRecommendationService: 'هذه الخدمة تقدم لك تقرير مفصل يحتوى على ثلاث إلى أربع مجلات موصى بها من قبل الخبراء بناء على نتائج بحثك في غضون ثلاثة إلى أربعة أيام. هذا الإختيار سوف يطابق موضوع النتائج الخاصة بك ويستند إلى نطاق المجلة وعامل التأثير وكذلك عوامل أخرى. في هذه الخدمة لايتم مراجعة المحتوى أو ضمان قبول مسودة بحثك.\n',
      conferencePosterService: 'من خلال خدمتنا في أنشاء لوحة ملصقة لعرضها في المؤتمرات العلمية. بناء على بيانات أبحاثك سوف نقدم لك عرض رائع يليق بعملك . نهتم بعرض ابحاثك واعمالك بصورة جذابة تلفت نظر الخبراء في ميدان عملك. وفي هذه الخدمة سوف تحصل على ملف عرض تقديمى قابل للتعديل وصورة عالية الدقة منسقة حسب متطلبات وارشادات المؤتمر العلمى أو الإجتماع.\n',
      researchNewsStoryService:`<p><b>إنه لمن الرائع أن تقدم بحثك الجديد للجمهور العام ، </b></p>
<p>
في هذه الخدمة سيتم اختصار عملك وتحويله في قصة مبسطة وشيقة تبرز عملك للقراء من العامة. نشر عملك المميز بين الجمهور على شكل خبر يمكن نشره في الجرائد  او مواقع التواصل الاجتماعى ويتضمن روابط ومفاتيح كلمات للبحث وايضا اختصارات مرتبطة بعملك الاصلى. نشر انجازاتك  العلمية على شكل قصة شيقة ومعبرة سيعرف الجماهير والمجتمع بإكتشافاتك الجديدة وبشكل مبسط وسلس. 
طريقة عمل القصة العلمية:إعطاء بحثك الذي تم قبوله في مجلة علمية  المراد صياغتها في قصة او خبر علمى. 
سيقوم خبراء ومحرريين علميين بعمل ملخص مبسط وشيق يلم بجميع نتائج عملك وابراز الاكتشافات الهامة 
قيام كبير المحررين بالمراجعه والتاكد من دقة الملخص 
يمكنك تنزيل قصة الاخبار على شكل ملف وورد
</p>
`,

      testimonialName1:'Dr. Wilson\n',
      testimonialContent1:'AESHشاركت  في تحسين محتويات مسودة بحثي العلمى وتقديم عملي البحثي بطريقة أكثر أناقة وبطريقة علمية سليمة. في النهاية ، حصلت على مقالتي المقبولة في مجلة ذات عامل تأثير أعلى بكثير مما كنت أتوقعه في أي وقت مضى.\n',
      testimonialName2:'Dr. Ahmed\n',
      testimonialContent2:'كنت مهتما  بتقديم عملي في مؤتمر دولي وكنت بحاجة إلى بعض الخبراء في مجال بحثي لمساعدتي في تقديم عملي المهم بطريقة أكثر مهنية وملخصة لكل عملى وجذب انتباه الجمهور في وقت قصير. كان AESH هو الخيار المناسب للقيام بكل العمل وإبراز ذلك في عرض تقديمي ممتاز. خالص تقديري لجهودهم.\n',
      testimonialName3:'Prof. Xu Weili\n',
      testimonialContent3:'AESH سريعين جدا في عملهم ، وبسعر جيد جدًا وتقوم بعمل ممتاز.قد قمت كباحث علمي بالعديد من تحرير اللغة الإنجليزية في عدد من الشركات وكانت AESH هي الأفضل. أعتقد أن مراجعين  المجلات لن تقول لنا بعد الآن أنه يجب مراجعة اللغة الإنجليزية لمسودتى العلمية بواسطة متحدثين أصليين.\n',
      whyOurService:'We welcome you to the home of professional, scientific writers. Our aim is to support authors in\n                    publishing their research with complete article preparation and writing support services.',
      confidentialityContent:'نحن نلتزم بسياسة خصوصية صارمة تضمن السرية بنسبة 100٪ وتسمح للباحثين في جميع أنحاء العالم بإرسال أعمالهم إلينا بثقة تامة. يتم إلزام جميع المحررين والمراجعين وطاقم العمل بتوقيع تعهد بالإلتزام بخصوصية وضمان عمل العملاء.\n',
      qualityGuaranteeContent:'يمكنك الاعتماد علينا للتميز والخبرة والاستجابة فيما يتعلق بجميع خدماتنا. إذا قمنا بتأخير عملك أو تم رفض مسودتك العلمية المحررة بسبب جودة اللغة أو هيكلها العلمى وتنسيقها، فإننا نقدم إعادة تحرير مجانية أو يتم استرداد الأموال بالكامل.\n',
      aesh:'Alpha Editorial Science Hub',
      aeshPunch:'Transforms the vision into reality',
      aeshContent:'Now our services supported by the science and medical professionals have the full suite of services\n  that include Academic Translation, Scientific Writing, Figure Services, Manuscript Formatting and\n others to increase your manuscript acceptance and to extend the impact of your published research.',
      getStarted:'Get Started',
      contactUs:'اتصل بنا',
      step1:'ها لعملك1',
      step2:'يل الطلب2',
      step3:'بية طلبك3',
      step4:'النهائية4',
      step1Content:'الخطوة 1: حدد الخدمة التي تحت',
      step2Content:'الخطوة 2: قم بتحميل ملفات المشروع والمعلومات الخاصة بك واستكمل تفا',
      step3Content:'الخطوة 3: اعمل مع خبير (خبراء) في مجال بحثك ل',
      step4Content:'الخطوة 4: إتمام الدفع وتنزيل ملفاتك',
      services:'خدمات',
      our:'Our',
      viewAllServices:'View all services',
      englishLanguageEditing:'تحرير اللغة الإنجليزية\n',
      scientificEditing:'التحرير العلمي',
      academicIllustration:'التوضيح الأكاديمي',
      conferencePosterCreation:'إنشاء ملصق المؤتمر',
      researchNewsStories:'قصص الأخبار البحثية',
      journalRecommendation:'توصية المجلة',
      figureFormatting:'تنسيق الشكل',
      graphicalAbstract:'خدمة مجردة رسومية',
      readMore:'اقرأ أكثر',
      whyOurServices:'لماذا خدماتنا بالذات؟!\n',
      confidentiality:'السرية التامة\n',
      qualityGuarantee:'ضمان جودة العمل والتسليم في الوقت المحدد\n',
      ourTestimonials:'Our testimonials',
      whatOurClientsThink:'What our clients think',
      testimonialContent:'Lorem ipsum dolor sit amet consectetur adipiscing elitsed do eiusmod tempor incididunt utlabore et\n dolore magna aliqua.\n Utenim ad minim veniam quis nostrud exercitation ullamco .',
      englishLanguageEditingPageContent:`<p><b>نقوم بتعديل  أخطاء اللغة الإنجليزية بطريقة أصلية ومنقحة.</b></p>
<ul> 
<li>إعادة الصياغة والتهجئة وعلامات الترقيم وتصحيح قواعد اللغة </li>
<li>تعديل تركيب الجمل والتأكد من  وضوح  وسلاسة محتوىات العمل</li>
<li>تقليل عدد الكلمات وفحص محتويات البحث العلمي </li>
<li>تقديم وصفًا تفصيليًا للأخطاء الشائعة الموجودة في كتابتك والتي قد تساعدك في الحصول على أفكار لتحسين عملك في المستقبل</li>
</ul><br>
<p>في هذه الخدمة، سيضمن الخبراء  في مجال بحثك أن أساسيات ومحتويات عملك البحثي مبينة بوضوح ، ويتم الحفاظ على تسلسل محتويات العمل  مع الإشارة إلى إرتباك المحتويات.<br></p>
<p><b>ملاحظة:</b>في هذه الخدمة لن يقوم المحررون بتحسين محتويات البحث أو إعادة هيكلة مسودة العمل أوتجهيزها للنشر في مجلة</p>
`,
      scientificEditingPageContent:`<p><b>نشاركك الرحلة المرهقة للنشر والبحث العلمي</b></p>
<p>
في هذه الخدمة ، سيقوم خبير في مجال عملك بكتابة مسودة مشروعك والتحضير للنشر أو التقديم  وتقديم نصائح إستراتيجية لعملك (مسودة بحثية، منحة مالية، أو أطروحة) في المجالات المتعلقة بالعلوم الطبيعية مثل الطب وعلم الأحياء. ستكون جودة التحرير وفقًا للمعايير العلمية الدولية التي ستتيح لك إرسال مخطوطتك بثقة. المحررون الخبراء في مجالك دائمًا في متناول يدك لمساعدتك على تحمل أعباء كتابتك العلمية وتعديل مخطوطتك بدقة. بالإضافة إلى ذلك ، سيتم تقديم تقرير شامل وملخص مفصل لعملك.</p> 
<p><b>سيتم تقييم الكتابة العلمية من قبل محررين علميين متخصصين</b></p>
<p><ul>
<li>الكتاب العلميين المتحمسين والمؤهلين الحاصلين على درجة الدكتوراه والملتزمين بأخذ الكتابة العلمية كمهنة سيقيمون عملك بعناية وعمل تقرير مفصل لعملك.</li>
<li>سيتم تقييم عملك بعناية والعثور على المشكلات في مشروعك وإعطاء الأولويات لإصلاح الأخطاء وتقديم تقرير مفصل يصف جميع المشكلات في عملك.</li>
</ul></p>

<p><b>ضمان الجودة والدعم المستمر</b></p>
<p>
يتم تمديد ضمان العمل لمدة ستة أشهر ، حيث يمكن إعادة تعديله مجانًا.
ملاحظة: سيكون هذا غير صالح إذا تم تحرير عملنا بواسطة شخص آخر.
إذا تم رفض العمل من قبل إحدى المجلات بسبب اللغة الإنجليزية أو بنية المخطوطة ، فسنقوم بإعادة التحرير مجانًا (على الرغم من أننا لم نشهد مثل هذه الحالات من قبل).
</p>`,
      researchNewsStoriesPageContent:'',
      academicIllustrationPageContent:'',
      conferencePosterCreationPageContent:`<p><b>اعرض انجازاتك العلمية في المؤتمرات العلمية والمحافل الدولية 
لماذا قد نحتاج خدمة انشاء الملصقات العلمية؟
</b></p>
<p>
المهتمون والمتخصصون العلميون خلال المؤتمرات والمعارض العلمية  يهتمون بزيارة معرض الملصقات العلمية وتقييم مدى اهمية البحث وخلق علاقات مع خبراء في مجال بحثك العلمى.
مدى دقة تلخيص بحثك وعرضه لمفاهيم نتائجك العلمية  وايضا العرض الفني والتنسيق لمحتويات الملصق تلعب دورا في اظهارك للمجتمع العلمي وتتيح لك الفرصة لمناقشة اكتشافاتك الهامة مع خبراء في مجالك.
من خلال خدمة إنشاء ملصق المؤتمر ، ندع أبحاثك تقف عالياً. استنادًا إلى بياناتك الداعمة والمقالات المنشورة والملفات الداعمة ، ينشئ الخبراء الأكاديميون في العمل الميداني ملصقات عالية التأثير وجذابة بصريًا لعرض نتائجك في المؤتمرات أو الاجتماعات. ستحصل على ملف PPTX عالي الدقة وقابل للتحرير وملف صورة جاهز للطباعة منسق وفقًا لإرشادات ملصق المؤتمر.
الخبراء والفنيين في مجال بحثك مستعدين لتعديل الملصق حتى تكون راض تماما ومطابق لمتطلبات مؤتمرك العلمي.
</p>
`,
      figureFormattingPageContent:`<p><b>استعين بخبرائنا العلميين والفنيين للتعامل مع التفاصيل التقنية لحفظ وقتك وجهدك الثمين.</b></p>
<p>
عرض الاشكال العلمية في بحثك مهم جدا لعرض نتائج ابحاثك وخلق انطباع سريع لدى محرري ومراجعين المجلة. </p>
<p><b>متطلبات تنسيق الاشكال العلمية </b></p>
<p>
على المؤلفين للبحث ان يزودوا المحرر التقنى المختص بملفات الاشكال القابلة للتعديل وايضا تفاصيل ومحتوى الشكل والمجلة المطلوبة للنشر. 
المصممين العلميين سيقومون بتنسيق الشكل حسب إرشادات العمل الفنى الخاص بالمجلة ومراجعته من قبل مختصين في مجال بحثك العلمى للتاكد من دقته ومطابقته للعمل المطلوب. 
تزويد المؤلفون بملف قابل للتعديل وايضا ملف بالصيغة والجودة المطلوبه بالمجلة العلمية
 
</p>
`,
      journalRecommendationPageContent:`<p><b>هل أنت محتار كيف تختار مجلة علمية مناسبة لبحثك من بين الاف المجلات
بمعيتنا سوف نكون معك خطوة بخطوة لتحضير بحثك العلمي من التعديل وحتى التسليم للمجلة .
لماذا تحتاج خبير علمي متخصص لتقديم الاستشارة في اختيار مجلة مناسبة لبحثك؟
</b></p>
<p>
هل تم رفض بحثك لعدم مطابقته نطاق المجلة 
المجتمع العلمي يحوي الاف المجلات العلمية ومن الشائع جدا رفض البحث لعدم مطابقته نطاق المجلة والذي قد يهدر ثمن وقتك وجهدك. هناك عدد من المعايير يتم مطابقتها قبل تسليم بحث لمجلة معينة مثل نطاق المجلة، معامل التأثير، فهرس المجلة، مدة المراجعة، دار النشر وغيرها من المعايير.
بمساعدة متخصصين في مجال بحثك العلمى سيتم دراسة بحثك وعمل مقارنة بابحاث علمية في مجال بحثك وتقييم نتائجه وايضا متطلبات المؤلفين وإختيار ثلاثة الى اربعة مجلات علمية مع ملخص شامل لكل معايير المجلة وشرح مبسط لسبب اختيار المجلة في تقرير مرفق مع بحثك. 
ملاحظة:  خدمة اقتراح مجلة علمية قد توفر الوقت والجهد وتزيد من احتمالية قبول بحثك لكن لاتضمن قبول البحث من قبل المجلة لان هذا معتمد بشكل كبير على جودة البحث وقرار محرري المجلة المعتمد على عدة عوامل.
</p>`,
      graphicalAbstractPageContent:`<p><b>جذب إنتباه الحاضرين عبر رسم توضيحي إحترافي ونموذجي 
لماذا تحتاج رسم توضيحي أكاديمي لبحثك؟
</b></p>
<p>
لعرض بيانات ومفاهيم بحثك تحتاج الى رسم توضيحي يشرح محتويات بحثك المعقدة والطويلة في شكل يختصر كل معلومات بحثك في عمل فنى مذهل ولافت للنظر تحتاج الى خبراء علميين وفنيين متخصصين في مجال بحثك. متخصصين في مجال بحثك مهتمين ومحترفين في الرسوم العلمية التوضيحية حيث يقومون بعمل رسم توضيحي يناسب تعليمات المجلات العلمية العالمية من حيث الجودة والقدرة على عرض محتويات ومفاهيم بحثك. 
الخطوات المتبعة في عمل رسم توضيحي اكاديمى 
</p>
<p><ul>
<li>تزويدنا بصورة مرسومة يدويا او مخططات نموذجية قد تكون مرفقة بملخص لمسودتك العلمية ومناقشة مختص في مجالك لتوضيح متطلباتك </li>
<li>يتم تسليم مشروعك إلى محترفين في الرسم العلمي مع مراعاة ارشادات المجلة العلمية </li>
<li>يتم مراجعتها من قبل رؤساء العمل وخبراء في مجالك العلمي للتاكد و التحري من دقة العمل من الناحية الفنية و العلمية</li>
<li>تسليم البحث على شكل ملف بي دي اف أو نسخة أخرى حسب طلب المؤلفين</li>
<ul>
</p>
<p>ملاحظة: خبرائنا الفنيين والعلميين على كامل الاستعداد لإعادة مراجعة الشكل التوضيحى حتى تكون راض تماما من العمل حتى بعد أشهر من تسليم العمل. هذا لايشمل الشكل الذي تم تعديله من قبل جهة اخرى.
</p>

`,
      aboutUs:'معلومات عنا',
      aboutUsTag:'يجب أن نتخلى عن الحياة التي خططنا لها ، حتى نقبل الحياة التي تنتظرنا\n',
      aboutUsContent:`<p>نرحب بكم في عالم العلوم الطبيعية المهنية والكتاب والمحررين الطبيين. هدفنا هو دعم المؤلفين في نشر أبحاثهم من خلال إعداد كامل للمقالات وخدمات دعم الكتابة. هنا في ASEH ، نحن ملتزمون بتوفير وقتك وجهودك الثمين ومساعدة المؤلفين على النجاح في مهنتهم. الآن خدماتنا مدعومة بالعلم والمهنيين الطبيين لديها مجموعة كاملة من الخدمات التي تشمل التحرير الأكاديمي وتحرير اللغة الإنجليزية وإنشاء ملصق المؤتمر وما إلى ذلك ، لتعزيز نطاق قبول مشاريعك وأبحاثك العلمية وتوسيع تأثير بحثك المنشور .
</p>`,
      faq:'Frequently asked questions',
      faqTag:'Listed questions and answers, all supposed to be commonly asked in some context, and pertaining to a particular topic.',
      generalQuestions:'General questions',
      q1:'هل يمكنني طرح أسئلة على المحرر الخاص بي فيما يتعلق بتعديل بحثي أو مشروعي العلمي؟',
      ans1:'بالتأكيد يمكن للمؤلف أن يسأل المحرر عن عمله بعد اكتمال عملية التحرير. يمكنك التواصل معنا من خلال خدمة اتصل بنا.',
      q2:'ما مدى اختلاف التحرير الأكاديمي عن التحرير باللغة الإنجليزية؟',
      ans2:'سوف يركز تحرير اللغة الإنجليزية على الصياغة واختيار الكلمات والأخطاء والقواعد وعلامات الترقيم والتحسينات اللغوية الأخرى بواسطة خبراء اللغة الإنجليزية. بينما سيضمن التحرير العلمي المحتويات العلمية ويضمن أن يتم تحرير عملك علميًا بواسطة خبير في مجالك.',
      q3:'هل يمكنني الحصول على فاتورة لطلبي؟',
      ans3:'عند تحميل ملفاتك، ستتلقى فاتورة عبر الإنترنت.',
      q4:'هل يمكنني الدفع بعملتي المحلية؟',
      ans4:'العملات الرئيسية مقبولة. يرجى تحديد عملتك عند تحميل ملفاتك.',
      q5:'هل يمكنك تنسيق المراجع الخاصة بي وما هو برنامج مدير المراجع الذي تدعمه؟',
      ans5:'نعم ، نقوم بتنسيق المراجع وحالياً ، ندعم برنامج Endnote.',
      q6:'إذا كانت مخطوطة تحتوي على أشكال وجداول ، فهل أحتاج إلى تنسيقها؟',
      ans6:'إذا اخترت خدمة التحرير العلمي ، فسيتم تضمين التنسيق. إذا لم يكن الأمر كذلك ، يمكنك اختيار خدمة تنسيق الأشكال ، والتي يمكن أن تساعدك في إعداد الجداول والأشكال.',
      q7:'هل التنسيق مختلف عن التحرير؟',
      ans7:'نعم ، سيركز الخبير في تنسيق الشكل على الشكل والجدول والتأكد من توافق جميع محتويات الأشكال أو الجداول مع تعليمات مؤلف المجلة. من ناحية أخرى ، لن يحضر المصمم أي تحرير باللغة الإنجليزية.',
      q8:'هل وثيقتي آمنة مع AESH؟',
      ans8:'تحتفظ AESH بموقع آمن ويجب على جميع موظفينا التوقيع على اتفاقية سرية لحماية عملك تمامًا.',
      q9:'كيف أحصل على عدد دقيق للكلمات في بحثي؟',
      ans9:'أولاً ، تقوم بإزالة جميع الكلمات التي لا تتعلق بعملك مثل جداول الأشكال والمراجع.',
      downloadReport: 'تنزيل التقرير‎',
      selectServices:'حدد الخدمات',
      checkout:'الدفع',
      getQuote:'الحصول على الاقتباس',
      moreInfo: 'مزيد من المعلومات',
      action: 'عمل,',
      resetPassword: 'إعادة تعيين كلمة المرور',
      selectTitle: 'حدد العنوان',
      countryCode: 'الرقم الدولي',
      invalidCountryCode: 'رمز الدولة غير صحيح',
      alreadyHaveAnAccount:'هل لديك حساب؟',
      dontHaveAnAccount:'ليس لديك حساب؟',
      forgotPassword:'نسيت رقمك السري؟',
      areYouSureLogout:'هل أنت متأكد من تسجيل الخروج؟',
      logout:'تسجيل خروج',
      currentPage: 'الصفحه الحاليه',
      resendToken: 'إعادة إرسال رمز التحقق؟',
      token: 'إعادة إرسال',
      goBack: 'عد',
      youCanTrustUs:'You can trust us',
      youCanTrustUsContent:'خدماتنا تنجز من قبل محررين علميين ذوي خبرة عالية ولديهم الدافع في تحرير الأبحاث العلمية  مع موضوع البحث الخاص بك ويتم مراجعتهم باستمرار من أجل الجودة لضمان معايير ASEH. نحن نقدم السرية والنزاهة جنبًا إلى جنب مع مجموعة متنوعة من الخدمات التي ستساعدك على تقديم عملك والترويج له بكفاءة ومهنية عالية.\n',
    },
  },
  cn: {
    translation: {
      signUp: '报名',
      login: '登录',
      home:'家',
      menu:'菜单',
      upload:'上传',
      iNeedHelp:'我需要帮助',
      file: '文件',
      noOfFigure:'图数',
      faqq:'常见问题',
      pricing:'价钱',
      loginSignup:'登录/注册',
      dashboard:'仪表板',
      uploadFile:'上传文件',
      trackFile:'跟踪文件',
      orderDetails:'订单详细信息',
      profile:'轮廓',
      findFile: '查找文件',
      newFile: '新文件',
      sNo: '序列号',
      topic: '话题',
      category: '类别',
      uploadDate: '上传日期',
      requestServeDate: '请求服务日期',
      edit: '编辑',
      signUpButtonText: '报名....',
      firstName: '名字（给定的）',
      middleName: '中间名（可选）',
      lastName: '姓氏（家庭）姓名',
      username: '用户名',
      primaryEmail: '主要电子邮件地址',
      phone: '电话',
      password: '密码',
      personalDetails: '个人资料',
      enterFirstName: '输入名字',
      enterMiddleName: '输入中间名（可选）',
      enterLastName: '输入姓氏',
      enterPhone: '输入电话号码',
      mobile: '移动的',
      enterMobile: '进入手机无效的电话号码',
      enterPrimaryEmail: '输入主要电子邮件',
      secondaryEmail: '辅助电子邮件',
      enterSecondaryEmail: '输入辅助电子邮件',
      address: '地址',
      enterAddress: '输入地址',
      city: '城市',
      state: '状态',
      country: '国家',
      postalCode: '邮政编码',
      education: '教育',
      institution: '机构',
      enterInstitution: '进入机构',
      department: '部',
      enterDepartment: '进入部门',
      cancel: '取消',
      update: '更新',
      editFile: '编辑文件',
      fileUpload: '上传文件',
      description: '描述',
      enterEmail: '输入你的电子邮箱',
      confirmPassword: '确认密码',
      invalidEmail: '无效的邮件地址',
      upperCase: '至少一个大写',
      lowerCase: '至少一个小写',
      oneDigit: '至少一位',
      oneSpecialCharacter: '至少一个特殊字符',
      invalidPhone: '无效的电话号码',
      verificationLinkMessage: '验证链接已发送至您的邮箱。请验证您的帐户',
      passwordResetMessage: '密码重置链接已发送至您的邮箱。请重新设置您的密码',
      passwordConfimation: '密码确认不匹配。',
      profileUpdateMessage: '个人资料更新成功',
      action: '行动',
      type: '类型',
      modifyDate: '修改日期',
      uploadBy: '上传者',
      downloadFile: '下载文件',
      language: '语言',
      wordsCount: '字数',
      servicesIWant: '我想要的服务',
      reports: '报告',
      status: '状态',
      fileId: '文件编号',
      reportId: '报告 ID',
      orderInfo: '订单信息',
      orderDate: '订购日期',
      expiryDate: '到期日',
      selectedServices: '选定的服务',
      estimatedAmount: '估计金额',
      remark: '评论',
      transactionId: '交易编号',
      totalAmountPaid: '支付总额',
      dateOfPayment: '付款日期',
      paymentMode: '付款方式',
      uploadSnapOfPaymentProof: '上传付款证明快照',
      order: '命令',
      reportType: '报告类型',
      proposedServices: '提议的服务',
      payment:'支付',

      ourServices:'我们的服务',
      ourServicesContent:'我们可为医学和自然科学相关领域的研究人员提供编辑论文、基金、标书等服务。我们的服务包括英文论文润色服务、科学编辑服务等。并以专业科学的方式展示您的学术研究亮点，提高您的论文质量，为您的职业生涯添砖加瓦。\n',
      ourServicesIntro: 'Alpha Editorial Science Hub \n We help authors to write their manuscript, thesis, grant and others. Our services supported by the science and medical professionals have the full suite of services that include English Editing, Academic Writing, Figure and Tables formatting Services, Manuscript preparation services and others to increase your manuscript acceptance and extend the impact of your published research.',
      englishLanguageService: '英文编辑与润色由英语母语、专业领域中的专家完成，确保您的研究成果能够清晰的、以原汁原味的英文呈现出来。\n',
      scientificEditingService: '"*指定专业领域的博士进行编辑，从而达到国际高影响力期刊标准\n' +
          '*深度审查您的学术内容并评估每个方面\n' +
          '*高质量控制体系，全力助您实现高质量发表"\n',
      academicIllustrationService: 'Present your essential findings in compelling and visually appealing illustrations to promote your work on social media, conferences, interviews or journals. Graphical abstract helps you present your hard work to the readers in a glance.\n',
      figureFormattingService: '*将您的图片格式与您的目标期刊相匹配，从而实现重要成果的最佳布置和展示 \n',
      graphicalAbstractService: '"*图文摘要可以帮助您向读者简约形象的展示您的研究内容\n' +
          '在重大会议、演讲、采访中，一张图片胜过千言万语，可更快、更容易的呈现您的研究内容。"\n',
      journalRecommendationService: '根据您的研究成果，我们将在3-4天内为您提供一份详细报告为您推荐3-4个期刊。我们的推荐基于期刊领域、影响因子和其他因素考虑，与您的研究课题相匹配。这里我们不做同行评议，不能保证您的稿件接受。\n',
      conferencePosterService: '我们的会议海报创作服务，可让您的研究成果高质量呈现。根据您的支撑数据、发表的文章和支撑文件等，我们专业领域人员可为您的会议制作一份高影响力的，引人入胜的文件，您将获得一份高质量的、可编辑的、格式符合会议或海报要求的PPT。\n',
      researchNewsStoryService: '使用这项服务，您可自如地与大众/社交媒体分享您有价值的研究成果。除了一份精简的300-500字的报道，我们还会提炼您的关键字、标签以及链接到您文章的DOI。此外，你会得到可在社交媒体使用的描述文本，如LinkedIn, Twitter等。通过这项服务可更广泛的与同行领域人员分享您的成果。\n',
      testimonialName1:'Dr. Wilson\n',
      testimonialContent1:'AESH参与了我的科研稿件内容的完善，使我的科研工作更加优雅、科学。最后，我的文章被比我预想的高得多的影响力的杂志所接受。\n',
      testimonialName2:'Dr. Ahmed\n',
      testimonialContent2:'我之前想参加一个国际会议，需要我研究领域的专家来帮助我以更专业的方式展示我的研究成果，并在短时间内总结所有的工作，呈现给大家。AESH是正确的选择，通过出色的报告中展示了这一点。我衷心感谢他们的努力。\n',
      testimonialName3:'Prof. Xu Weili\n',
      testimonialContent3:'AESH的工作速度非常快，定价非常合理，而且做得非常好。作为一名学者，与很多英文翻译的公司合作过，其中，AESH是最好的。期刊审稿人从此不会再说，我的论文应该由以英语为母语的人进行校核。\n',
      whyOurService:' 我们的编辑和校对均为英语母语、专业领域的博士。我们的目标是用我们专业的服务支持作者顺利发表他们的研究成果。\n',
      confidentialityContent:'我们秉持严格的隐私政策，保证100%保密，确保世界各地的研究人员提交的文件不会泄露。所有编辑、校核人员和工作人员均签署了关于客户工作的隐私和保证的承诺。\n',
      qualityGuaranteeContent:'我们卓越、专业的知识、及时的响应值得您的信赖，如果由于我们工作的延迟，或您的编辑稿件因语言质量或结构问题被拒绝，我们将提供免费的重新编辑或全额退款服务。\n',
      aesh:'Alpha Editorial Science Hub',
      aeshPunch:'Transforms the vision into reality',
      aeshContent:'Now our services supported by the science and medical professionals have the full suite of services\n  that include Academic Translation, Scientific Writing, Figure Services, Manuscript Formatting and\n others to increase your manuscript acceptance and to extend the impact of your published research.',
      getStarted:'Get Started',
      contactUs:'联系我们',
      step1:'第一步',
      step2:'第二步',
      step3:'第三步',
      step4:'第四步',
      step1Content:'选择您需要的服务',
      step2Content:'上传您的文件和信息，并完成订单细节',
      step3Content:'与我们的专业领域人员合作，以满足您的要求。',
      step4Content:'完成付款并下载最终文件',
      services:'服务',
      our:'Our',
      viewAllServices:'View all services',
      englishLanguageEditing:'英文论文润色',
      scientificEditing:'科学编辑',
      academicIllustration:'学术插图',
      conferencePosterCreation:'会议海报制作',
      researchNewsStories:'研究新闻故事',
      journalRecommendation:'期刊推荐',
      figureFormatting:'图形格式',
      graphicalAbstract:'图形抽象服务',
      readMore:'阅读更多',
      whyOurServices:'为什么选择我们的服务\n',
      confidentiality:'保密工作\n',
      qualityGuarantee:'工作质量保证和准时交货\n',
      ourTestimonials:'Our testimonials',
      whatOurClientsThink:'What our clients think',
      testimonialContent:'Lorem ipsum dolor sit amet consectetur adipiscing elitsed do eiusmod tempor incididunt utlabore et\n dolore magna aliqua.\n Utenim ad minim veniam quis nostrud exercitation ullamco .',
      englishLanguageEditingPageContent:`<p>"我们修改语法错误，地道清晰的表达，让您的文字如出自英语为母语者之手</p>
<ul>
<li>改述、拼写、标点和语法校正</li>

<li>调整句子结构，确保文章内容清晰流畅</li>

<li>改述不自然和表意不清的句子</li>

<li>为您在写作中常见的错误提供总结报告，帮助您在未来工作中得到改进</li>
</ul>
<p>在这项服务中，与您研究领域匹配的专家将确保您的研究内容的逻辑清晰、语言精准。</p>
<p>
注意:在这项服务中，工作人员将不会更改您研究的内容。"
</p>`,
      scientificEditingPageContent:`<p><b>我们与您共同承担出版过程的压力</b></p>
<p>
您的研究论文、综述或经费申请书，可获得遵循自然科研质量标准的编辑团队提供的专家级深入编辑和策略性意见。论文科学研编辑服务包含金牌英文润色、发展性编辑、质保编辑和一份总结报告。我们擅长编辑医学和生物学等自然科学相关领域的研究论文，经费申请和综述。

对您的科研稿件进行实质性的深入评估
</p>
<p><ul>
<li>组在您的领域富有经验的博士级编辑团队将仔细评估您的稿件和所有随附资料，深度审查您的学术内容并评估每个方面</li>

<li>战略性的发展性编辑，为作者进一步修改提供精辟的建议，这将有助于您最大限度地提高稿件的有效性。</li>
</ul></p>
<p>

我们将在完成初始订单后6 个月内提供一次免费语言编辑（针对特定研究领域的语法、措辞和标点符号校正）。

注意:如果我们的稿件被其他人编辑，这将是无效的。

如果稿件因为语言或结构问题被杂志社拒绝，我们会免费重新编辑(虽然我们从未遇到过这样的情况)。</p>
`,
      researchNewsStoriesPageContent:`<p><b>将您新研究进展进行广泛推广意义重大</b></p>
<p>
在这项服务中，您的工作将被总结精简成一个简短有趣的故事，突出您的工作。以新闻的形式在报纸或社交媒体上发布您的研究报道，为您提炼出标签和关键字等，以方便找到您的研究，以及与您相关的文章。以有趣、富有表现力的故事形式展示您的科学成就。

科学报道服务如何实现:提交您已发表的文章，这些文章可在科学报道或新闻中加以阐述。

专业团队会撰写新闻研究报道，总结提炼您所有的科研成果，并突出您的研究中的重要发现

与您研究领域匹配的资深编辑将审查和核验摘要的准确性

您可以下载Word版本的新闻报道
</p>
`,
      academicIllustrationPageContent:'',
      conferencePosterCreationPageContent:`<p><b>在科学会议或国际论坛上展示你的科研成果

为什么需要会议海报创作服务?
</b></p>
<p>
在科学会议或展览期间，很多相关领域的学者会有兴趣参观海报展览，评估研究的重要性，并与相关科学研究领域学者沟通交流。

您研究成果总结的准确性、您科研成果的呈现均以技术报告或海报的形式展现，这将使您与同领域学者更好的沟通交流。

我们的会议海报创作服务，可让您的研究成果高质量呈现。根据您的支撑数据、发表的文章和支撑文件等，我们专业领域人员可为您的会议制作一份高影响力的，引人入胜的文件，您将获得一份高质量的、可编辑的、格式符合会议或海报要求的PPT。

我们的专业团队可为您修改海报，直到您完全满意并符合您科学会议要求为止。
</p>
`,
      figureFormattingPageContent:`<p><b>我们的专家团队将为您处理技术细节，为您节省宝贵的时间和精力。</b></p>
<p>
以科学的方式呈现出您的研究结果是非常重要的，能够让期刊的编辑和审稿人对您的研究产生深刻的印象。</p>
<p><b>科学的格式排版</b></p>
<p>
您需提供我们可编辑的图表文件、图表的详细信息和内容，以及要投稿的目标期刊。

我们的设计团队根据期刊要求进行艺术性协调调整，并由专业领域专家团队进行审核，以确保其准确性和一致性。
</p>

<p><b>您将会得到:</b></p>
<p>
一份可编辑的文件，图表是按照目标期刊的格式和质量要求,并由专业领域专家团队进行审核，以确保其准确性和一致性。

根据您所选期刊的规范要求，我们会根据您所选择的期刊的要求进行调整，如分辨率、布局大小、字体、颜色、空间、比例、字体大小等。在这项服务中，我们的专家编辑将确保您的图表与杂志的要求一致。

您可以将您的图表上传给我们的编辑并要求提供报价。
 
</p>`,
      journalRecommendationPageContent:`<p><b>如何从成千上万的期刊中选择一份适合您研究成果的期刊杂志，您感到困惑吗？

我们将与您一起一步一步地准备您的研究成果，从修改到提交到期刊杂志。

为什么您需要专业人员为您选择期刊提供建议呢?

您的论文被拒绝是不是因为不符合期刊的选用范围?
</b></p>
<p>
科学界有成千上万的杂志期刊，因为不符合期刊范围而被拒是很常见的，这可能会浪费您大量的时间和精力。在向特定期刊提交研究之前，需要满足几个标准，如期刊的范围、影响因子、期刊索引、审查期、出版社和其他标准等。

在团队您所在科研领域的专家的帮助下，您的研究成果将被评估，我们可为您推荐三到四个期刊杂志，并附上期刊标准的综合总结和选择该期刊的简单解释。

注意:杂志推荐的服务将节省您的时间和精力,增大您文章被接受的可能性，但它并不能保证接受一定会被接收，因为这在很大程度上取决于您研究成果的质量和期刊编辑基于各种因素的决定。

</p>
`,
      graphicalAbstractPageContent:`<p><b>专业、富有表现力的插图可为您的文章画龙点睛

为什么你文章需要插图?
</b></p>
<p>
为了展示您的研究成果和理论概念，您需要图文摘要，将您的研究成果提炼出引人注目的、艺术性的图片来解释您复杂和冗长的研究内容，这需要与您研究领域相关的专家理解您的研究成果，才能保证图片呈现的质量，以呈现您的研究内容和理论概念。

学术插图服务涉及的步骤
</p>
<p><ul>
<li>向我们提供手绘图或原始图片，以及相关的科学研究的草稿，与我们技术团队沟通交流，明确要求</li>
<li>您的项目交付给我们专业的绘图团队，同时结合目标期刊的要求指南来进行调整</li><ul>
最终插图由团队专家进行审查，以确保插图的准确性

根据要求，以PDF文件或其他副本的形式提交您的研究文章
</p>
<p>
注意:我们的技术团队会全心全意为您修改插图，直到您完全满意为止。如插图被其他人修改，我们将不再提供服务。</p>

`,
      aboutUs:'关于我们',
      aboutUsTag:'避免一成不变，让生活有所期待。\n',
      aboutUsContent:`<p>我们欢迎您来到专业自然科学、医学作家和编辑的世界。我们的目标是为您提供写作支持服务从而使您的研究成果得以高质量发表。在ASEH，我们致力于为您节省宝贵的时间和精力，帮助您在您的职业领域取得成功。目前我们的服务由自然科学和医学专业博士级人员支持，包括英文润色、英文科学编辑、会议海报创作等全方位的服务，以提高科学研究的传播范围，扩大您研究成果的影响力。
</p>`,
      faq:'Frequently asked questions',
      faqTag:'Listed questions and answers, all supposed to be commonly asked in some context, and pertaining to a particular topic.',
      generalQuestions:'General questions',
      q1:'支持人民币支付吗?',
      ans1:'可以。请在上传文件时选择您的货币类型。',
      q2:'你能格式化我的参考文献吗?你支持哪些参考文献管理软件?',
      ans2:'可以，我们可以格式化引用，目前，我们支持Endnote软件。',
      q3:'如果我的原稿包含图表，我需要自己先排版么?',
      ans3:'如果您选择科学化编辑服务，图表格式是包括在内。如果没有，您可以选择我们的“图表排版”服务，它可以帮助您准备图表。',
      q4:'图表排版与编辑不同吗?',
      ans4:'是的，图表排版将专注于图、表，并确保所有手稿内容与期刊的要求一致。另一方面，工作人员将不参与任何英文编辑。',
      q5:'我的文档使用AESH是否安全可靠呢?',
      ans5:'AESH是一个安全的网站，我们所有的工作人员必须签署保密协议，以绝对保护您的工作。',
      q6:'我如何得到准确的单词计数?',
      ans6:'文章总字数为去除掉图表和参考文献的字数。',
      q7:'Is formatting different from editing?',
      ans7:'Yes, formatters will focus on the figure, table, and all manuscript contents are in agreement with journal authors instructions. In the other hand formatters  will not attend any English language editing.',
      q8:'Is my document safe and secure with AESH?',
      ans8:'AESH maintains a secure website and all our staff have to sign an confidentiality agreement to protect your work.',
      q9:'',
      ans9:'',
      downloadReport: '下载报告',
      selectServices:'选择服务',
      checkout:'退房',
      getQuote:'获得报价',
      moreInfo: '更多信息',
      action: '行动',
      resetPassword: '重设密码',
      selectTitle: '选择标题',
      countryCode: '国家代码',
      invalidCountryCode: '国家代码无效',
      alreadyHaveAnAccount:'已经有一个帐户？',
      dontHaveAnAccount:'没有帐户？',
      forgotPassword:'忘记密码了吗？',
      areYouSureLogout:'你确定要退出吗？',
      logout:'登出',
      currentPage: '当前页面',
      resendToken: '重新发送验证令牌？',
      token: '重发',
      goBack: '回去',
      youCanTrustUs:'我们值得信赖\n',
      youCanTrustUsContent:'我们的服务由与您研究领域相匹配的、经验丰富、热情积极的博士级团队完成，确保ASEH高质量服务标准。提供保密、诚信与各种服务相结合，帮助您高效、专业地呈现和推广研究成果。\n',
    },
  },
};

const setLocale = (language) => {
  i18next.init({
    fallbackLng: ['en'],
    lng: language,
    resources,
  });
};