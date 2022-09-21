using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
    public class high_tech_score : tablebase
    {
        [Key]
        public int id { get; set; }
        public int companyId { get; set; }
        public int year { get; set; }

        ///<summary>
        /// 研发个数
        ///</summary>
        public int? rdCnt { get; set; }
        ///<summary>
        /// 研发费（万元）
        ///</summary>
        [Column(TypeName = "decimal(18,2)")]
        public decimal? rdFunds { get; set; }
        ///<summary>
        /// 高品数
        ///</summary>
        public int? highTechCnt { get; set; }
        ///<summary>
        /// 高品编码
        ///</summary>
        [MaxLength(1000)]
        public string highTechCodes { get; set; }

        [Column(TypeName = "decimal(18,2)")]
        public decimal? income { get; set; }

        ///<summary>
        /// 先进得分
        /// </summary>
        public int? advanced { get; set; }
        ///<summary>
        /// 作用得分
        /// </summary>
        public int? effect { get; set; }
        ///<summary>
        /// 专利数量得分
        /// </summary>
        public int? patentAmount { get; set; }

        ///<summary>
        /// 获得方式得分
        /// </summary>
        public int? acquirement { get; set; }
        ///<summary>
        /// 获得方式:0：有自主研发，1：仅有受让、受赠和并购等
        /// </summary>
        public int? acquirementMode { get; set; }

        ///<summary>
        /// 是否参与编制国家标准、行业标准、检测方法、技术规范的情况（贡献得分）
        /// </summary>
        public int? contribution { get; set; }

        ///<summary>
        /// 科技成果转化得分
        /// </summary>
        public int? scienceResult { get; set; }

        ///<summary>
        /// 投入费用，辅助帐得分
        /// </summary>
        public int? generalLedger { get; set; }

        ///<summary>
        /// 开展合作得分
        /// </summary>
        public int? cooperation { get; set; }

        ///<summary>
        /// 建立激励开放式创新平台得分
        /// </summary>
        public int? excitation { get; set; }

        ///<summary>
        /// 培养、奖励得分
        /// </summary>
        public int? foster { get; set; }

    }
}