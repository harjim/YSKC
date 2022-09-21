using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
namespace MSDB.Models
{
    public class policySource : tablebase
    {
        [Key]
        public int id { get; set; }

        /// <summary>
        /// 域名
        /// </summary>
        [Required, MaxLength(100)]
        public string domain { get; set; }

        /// <summary>
        /// 来源名称
        /// </summary>
        [Required, MaxLength(50)]
        public string sname { get; set; }

        /// <summary>
        /// 来源类型
        /// </summary>
        [MaxLength(50)]
        public string stype { get; set; }

        /// <summary>
        /// 来源地址。主页
        /// </summary>
        [Required, MaxLength(300)]
        public string typeUrl { get; set; }

        /// <summary>
        /// 爬取地址
        /// </summary>
        [Required, MaxLength(300)]
        public string url { get; set; }

        /// <summary>
        /// 分页类型，1==>page = 1，2=>index.html,index_2.html...，3==>startRow = 1,endRow = 15,sept=15
        /// 1 单独传page ，2 html递增，3 sept * i++，默认区间1到15条数据
        /// </summary>
        public int pageType { get; set; }

        /// <summary>
        ///首页请求方法，值：POST，GET
        /// </summary>
        [Required, MaxLength(10)]
        public string method { get; set; }

        /// <summary>
        ///  method == POST，设置form表单数据，该数据为JSON格式。           
        /// </summary>
        [MaxLength(300)]
        public string formData { get; set; }

        /// <summary>
        ///  最大爬取页数：默认10        
        /// </summary>
        public int pageMax { get; set; }

        /// <summary>
        ///  首页请求解析方式:JSON,RE,XPATH,若为json ，默认处理：从json数据获取所有所需值     
        /// </summary>
        [Required, MaxLength(10)]
        public string dataParse { get; set; }

        /// <summary>
        /// 若dataParse == JSON,该字段需配置解析数据json，该属性值同样为JSON
        /// data = {"parent":{"parent2":{"parent3":["path":"http://xxx.com","title":"name","issueDate": "lastTime(时间戳)",...]}}}
        /// 格式： {"keys":["parent","parent2","parent3"],"url": "path","title": "name","issueDate":"lastTime","isTimeStamp": "False | True"}
        /// </summary>
        [MaxLength(300)]
        public string jsonFormat { get; set; }

        /// <summary>
        /// 分页内容解析：RE,XPATH，若dataParse !=JSON,该项必须，用于解析将要进入页面的url
        ///</summary>
        [MaxLength(100)]
        public string pageParse { get; set; }

        /// <summary>
        /// 内容解析方式：RE，XPATH，若dataParse !=JSON,该项必须，用于解析进入具体页获取title，issueDate
        /// </summary>
        [MaxLength(10)]
        public string dataContent { get; set; }

        /// <summary>
        /// title解析，由datacontent确定类型：RE,XPATH
        /// </summary>
        [MaxLength(100)]
        public string titleParse { get; set; }
        /// <summary>
        /// 日期（issueDate）解析，由datacontent确定解析类型：RE,XPATH
        /// </summary>
        [MaxLength(100)]
        public string dateParse { get; set; }

        /// <summary>
        /// 日期（issueDate）格式化。该项在RE，XPATH，JSON的jsonFormat["isTimeStamp"] = False时必须。
        /// </summary>
        [MaxLength(20)]
        public string dateFormat { get; set; }
        /// <summary>
        /// url拼接地址
        /// </summary>
        [MaxLength(200)]
        public string urlBase { get; set; }

        /// <summary>
        /// 默认携带cookies
        /// </summary>
        [MaxLength(300)]
        public string cookies { get; set; }
        /// <summary>
        /// 区域代码
        /// </summary>
        [MaxLength(30)]
        public string addressCode { get; set; }
        /// <summary>
        /// 表单类型： 默认空,默认json，
        /// </summary>
        [MaxLength(30)]
        public string formType { get; set; }
        public bool disabled { get; set; }
        /// <summary>
        /// 错误次数
        /// </summary>
        public int errCnt { get; set;}
    }
}