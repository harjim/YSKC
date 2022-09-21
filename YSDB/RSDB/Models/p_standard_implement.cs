using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
    /// <summary>
    /// 研发项目标准化实施情况汇总表
    /// </summary>
    public class p_standard_implement : tablebase
    {
         [Key]
        public int id { get; set; }

         /// <summary>
        /// 公司
        /// </summary>
        public int companyId { get; set; }
        /// <summary>
        /// 年份
        /// </summary>
        public int year { get; set; }

          /// <summary>
        /// 研发中心建设标准化
        /// </summary>
        [MaxLength(500)]
        public string rdCenterBuild { get; set; }
            /// <summary>
        /// 研发项目管理标准化
        /// </summary>
        [MaxLength(500)]
        public string rdProjectManage { get; set; }
            /// <summary>
        /// 研发费用核算标准化
        /// </summary>
        [MaxLength(500)]
        public string rdBudget { get; set; }

        /// <summary>
        /// 研发成果管理标准化
        /// </summary>
        [MaxLength(500)]
        public string rdResultManage { get; set; }
          /// <summary>
        /// 知识产权管理标准化
        /// </summary>
        [MaxLength(500)]
        public string intellectualManage { get; set; }
           /// <summary>
        /// 高新技术产品标准化
        /// </summary>
        [MaxLength(500)]
        public string highTech { get; set; }
        /// <summary>
        /// 研发档案管理标准化
        /// </summary>
        [MaxLength(500)]
        public string fileManage { get; set; }

        /// <summary>
        /// 研发意识管理标准化
        /// </summary>
        [MaxLength(500)]
        public string thoughtManage { get; set; }

    }
}