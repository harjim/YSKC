using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class d_salary_index : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateIndex(
                name: "IX_d_bonus_companyId_enumber_month",
                table: "d_bonus",
                columns: new[] { "companyId", "enumber", "month" },
                unique: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropIndex(
                name: "IX_d_bonus_companyId_enumber_month",
                table: "d_bonus");
        }
    }
}
