using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Threading.Tasks;

namespace MSDB.Models
{
    public class rsProject_summary
    {
        [Key]
        public int id { get; set; }
        public int companyId { get; set; }
        public int rsProjectId { get; set; }
        public int year { get; set; }
        ///<summary>
        /// 研发成员数
        ///</summary>
        public int? memberCnt { get; set; }
        ///<summary>
        /// 研发设备数
        ///</summary>
        public int? equipmentCnt { get; set; }
        ///<summary>
        /// 专利数
        ///</summary>
        public int? patentCnt { get; set; }
        ///<summary>
        /// 是否有立项报告
        ///</summary>
        public Boolean? hasReport { get; set; }
        ///<summary>
        /// 立项审核状态
        ///</summary>
        public int? reportStatus { get; set; }
        ///<summary>
        /// 是否有查新报告
        ///</summary>
        public Boolean? hasNewReport { get; set; }
        ///<summary>
        /// 查新报告状态
        ///</summary>
        public int? newReportStatus { get; set; }
        ///<summary>
        /// 文档数（已编写数）
        ///</summary>
        public int? docTotal { get; set; }
        ///<summary>
        /// 提交文档数
        ///</summary>
        public int? docSubmitCnt { get; set; }
        ///<summary>
        /// 通过文档数
        ///</summary>
        public int? docDoneCnt { get; set; }
        ///<summary>
        /// 留存备查资料数，跨年项目需区分年度总结报告及项目验收报告(通过数: (通过4个，即为完成)不包含人员折旧，项目编制，辅助帐)
        ///</summary>
        public int? backupDataTotalCnt { get; set; }
        public DateTime createTime { get; set; }
        public DateTime updateTime { get; set; }
        [MaxLength(50)]
        public String rdTitle { get; set; }
        [MaxLength(200)]
        public String pname { get; set; }

    }
}