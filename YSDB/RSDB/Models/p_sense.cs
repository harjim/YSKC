using System;
using System.ComponentModel.DataAnnotations;

namespace RSDB.Models
{
    public class p_sense : tablebase
    {
        [Key]
        public int id { get; set; }
        
        public int companyId { get; set; }

        ///<summary>
        /// 0:研发定义、范围、周期等引导及政策宣导，1:研发项目梳理、人员、设备界定引导，2:研发考勤小程序打开引导
        /// 3:研发机构建设事项引导，4:知识产权引导，5:高新技术领域及高新技术产品宣导，6:车间级研发管理思路引导及资料梳理
        /// 7:项目资料交付会签引导，8:研发项目税务核查引导、演练，9:过程材料及财务数据之间勾稽关系引导
        /// 10:研发成果宣讲、引导、梳理，11:阶段总结及问题讨论，12：其他引导文件
        ///</summary>
        public int type { get; set; }
        
        ///<summary>
        /// 宣讲日期
        ///</summary>
        public DateTime preachDate { get; set; }

        ///<summary>
        /// 关联RD
        ///</summary>
        [MaxLength(200)]
        public string rds { get; set; }

        ///<summary>
        /// 附件
        ///</summary>
        [Required,MaxLength(1000)]
        public string filePaths { get; set; }

        [MaxLength(500)]
        public string remark  { get; set; }

    }
}