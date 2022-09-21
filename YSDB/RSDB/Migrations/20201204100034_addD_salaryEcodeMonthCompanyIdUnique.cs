using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class addD_salaryEcodeMonthCompanyIdUnique : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateIndex(
                name: "IX_d_salary_enumber_month_companyId",
                table: "d_salary",
                columns: new[] { "enumber", "month", "companyId" },
                unique: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropIndex(
                name: "IX_d_salary_enumber_month_companyId",
                table: "d_salary");
        }
    }
}
