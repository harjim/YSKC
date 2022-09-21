using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace RSDB.Models
{
    /// <summary>
    /// 技改项目表
    /// </summary>
    public class t_project : tablebase
    {
        [Key]
        public int id { get; set; }

        public short pyear { get; set; }
        /// <summary>
        /// 项目名称
        /// </summary>
        [Required, MaxLength(200)]
        public string pname { get; set; }

        /// <summary>
        /// 申报方向
        /// </summary>
        [MaxLength(30)]
        public string reportType { get; set; }
        /// <summary>
        /// 扶持方式
        /// </summary>
        [MaxLength(30)]
        public string aidType { get; set; }
        /// <summary>
        /// 负责人
        /// </summary>
        [MaxLength(30)]
        public string masterName { get; set; }
        /// <summary>
        /// 负责人电话
        /// </summary>
        [MaxLength(30)]
        public string masterTel { get; set; }

        /// <summary>
        /// 联系人
        /// </summary>
        [MaxLength(30)]
        public string linkName { get; set; }

        /// <summary>
        /// 联系人电话
        /// </summary>
        [ MaxLength(30)]
        public string linkTel { get; set; }
        /// <summary>
        /// 申请日期
        /// </summary>
        public DateTime? applyDate { get; set; }

        public int companyId { get; set; }
        /// <summary>
        /// 备注
        /// </summary>
        [MaxLength(200)]
        public string remark { get; set; }

        /// <summary>
        ///开始日期 
        /// </summary>
        public DateTime? beginDate { get; set; }
        /// <summary>
        /// 结束日期
        /// </summary>
        public DateTime? endDate { get; set; }

        public int sourceProjectId { get; set; }

        public int directionId { get; set; }

        public int productId { get; set; }

    }
}
