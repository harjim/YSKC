using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class addP_fina_scheduleCompanyIdProjectIdMonthDeptNameUnique : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropIndex(
                name: "IX_p_fina_schedule_companyId_projectId_month",
                table: "p_fina_schedule");

            migrationBuilder.CreateIndex(
                name: "IX_p_fina_schedule_companyId_projectId_month_deptName",
                table: "p_fina_schedule",
                columns: new[] { "companyId", "projectId", "month", "deptName" },
                unique: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropIndex(
                name: "IX_p_fina_schedule_companyId_projectId_month_deptName",
                table: "p_fina_schedule");

            migrationBuilder.CreateIndex(
                name: "IX_p_fina_schedule_companyId_projectId_month",
                table: "p_fina_schedule",
                columns: new[] { "companyId", "projectId", "month" },
                unique: true);
        }
    }
}
