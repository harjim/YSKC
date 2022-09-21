using System;
using System.ComponentModel.DataAnnotations;
namespace RSDB.Models
{
    public class keypoint_statistic
    {

        [Key]
        public int id { get; set; }
        ///<summary>
        /// 客户数
        ///</summary>
        public int customerCnt { get; set; }
        ///<summary>
        /// 研发项目数
        ///</summary>
        public int rdCnt { get; set; }
        ///<summary>
        /// 专利数
        ///</summary>
        public int patentCnt { get; set; }
        ///<summary>
        /// 科研人数
        ///</summary>
        public int memberCnt { get; set; }
        public DateTime lastUpdateTime { get; set; }

    }
}