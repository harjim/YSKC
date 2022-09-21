using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class addP_outsourcingCompanyIdProjectIdTypeMonthUnique : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateIndex(
                name: "IX_p_outsourcing_companyId_projectId_type_month",
                table: "p_outsourcing",
                columns: new[] { "companyId", "projectId", "type", "month" },
                unique: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropIndex(
                name: "IX_p_outsourcing_companyId_projectId_type_month",
                table: "p_outsourcing");
        }
    }
}
