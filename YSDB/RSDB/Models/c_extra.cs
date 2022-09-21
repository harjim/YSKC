using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;
namespace RSDB.Models
{
    ///<summary>
    /// 公司信息扩展
    ///</summary>
    public class c_extra : tablebase
    {
        [Key]
        public int id { get; set; }
        public int companyId { get; set; }
        ///<summary>
        /// 经营地址
        ///</summary>
        [MaxLength(100)]
        public String manageAddress { get; set; }
        ///<summary>
        /// 注册所在城市
        ///</summary>
        [MaxLength(30)]
        public string registerAddrCode { get; set; }
        ///<summary>
        /// 注册地址
        ///</summary>
        [MaxLength(30)]
        public string registerAddress { get; set; }
        ///<summary>
        /// 注册类型
        ///</summary>
        [MaxLength(30)]
        public string registerType { get; set; }
        ///<summary>
        /// 主营产品
        ///</summary>
        [MaxLength(50)]
        public string mainProducts { get; set; }
        ///<summary>
        /// 办公所在区
        ///</summary>
        [MaxLength(30)]
        public string officeAddrCode { get; set; }
        ///<summary>
        ///合作组织数量
        ///</summary>
        public int? cooperateOrg { get; set; }
        ///<summary>
        ///内部研发机构数
        ///</summary>
        public int? insideRdOrg { get; set; }
        ///<summary>
        /// 生产所在区
        ///</summary>
        [MaxLength(30)]
        public string productAddrCode{get;set;}
        ///<summary>
        /// 办公用房面积
        ///</summary> 
        [Column(TypeName = "decimal(18,2)")]
        public decimal? officeArea{get;set;}
        ///<summary>
        /// 海外营销机构数
        ///</summary>
        public int? overSeaMarketings { get; set; }
        ///<summary>
        /// 全年用电量
        ///</summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal? yearKWh { get; set; }
        ///<summary>
        /// 生产用房面积 
        ///</summary> 
        [Column(TypeName = "decimal(18,2)")]
        public decimal? productArea{get;set;}
          ///<summary>
        ///海外研发机构数
        ///</summary>
        public int? overSeaRdOrg { get; set; }
        ///<summary>
        /// 全年用水量
        ///</summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal? yearMwo { get; set; }
        ///<summary>
        /// 是否统计联网直报企业
        ///</summary>
        public bool? onlineReport { get; set; }
        ///<summary>
        /// 是否拥有自营电子商务交易平台 
        ///</summary>
        public bool? ownerECP { get; set; }
        
        ///<summary>
        /// 电子商务交易平台名称
        ///</summary>
        [MaxLength(50)]
        public string nameECP { get; set; }
        ///<summary>
        /// 电子商务交易平台地址
        ///</summary>
        [MaxLength(200)]
        public string urlECP { get; set; }
        ///<summary>
        ///单位资质: 国家高新技术企业,深圳市高新技术企业,深圳市软件企业,服务外包企业,先进服务业
        ///</summary>
        [MaxLength(50)]
        public string qualification { get; set; }
    }
}