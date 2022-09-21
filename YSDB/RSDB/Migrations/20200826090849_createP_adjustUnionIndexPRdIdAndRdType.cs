using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class createP_adjustUnionIndexPRdIdAndRdType : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateIndex(
                name: "IX_p_adjust_pRdId_rdType",
                table: "p_adjust",
                columns: new[] { "pRdId", "rdType" },
                unique: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropIndex(
                name: "IX_p_adjust_pRdId_rdType",
                table: "p_adjust");
        }
    }
}
